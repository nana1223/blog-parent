package zn.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zn.blog.dao.mapper.CommentMapper;
import zn.blog.dao.pojo.Comment;
import zn.blog.dao.pojo.SysUser;
import zn.blog.service.CommentService;
import zn.blog.service.SysUserService;
import zn.blog.utils.UserThreadLocal;
import zn.blog.vo.CommentVo;
import zn.blog.vo.Result;
import zn.blog.vo.UserVo;
import zn.blog.vo.params.CommentParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangna
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public Result commentsByArticleId(Long articleId) {
        /**
         * 1.根据文章id，从comments表中 查询评论列表，先查第一层的评论 即level=1的，（查出来后再对所有level=1的去查第二层的评论）
         * 2.根据comments表中所存的作者的id 去sysUser表中 查作者信息
         * 3.判断 如果level=1，要查询它有没有子评论，若有，则根据评论id查询
         */
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId, articleId);
        queryWrapper.eq(Comment::getLevel, 1);
        //此时得到的评论列表，是该文章下所有的第一层评论
        List<Comment> commentList = commentMapper.selectList(queryWrapper);
        List<CommentVo> commentVoList = copyList(commentList);
        return Result.success(commentVoList);

    }

    @Override
    public Result comment(CommentParam commentParam) {
        SysUser sysUser = UserThreadLocal.get();
        Comment comment = new Comment();
        comment.setArticleId(commentParam.getArticleId());
        comment.setAuthorId(sysUser.getId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParam.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        }else{
            comment.setLevel(2);
        }
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        commentMapper.insert(comment);
        return Result.success(null);
    }

    private List<CommentVo> copyList(List<Comment> commentList) {

        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    private CommentVo copy(Comment comment) {

        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment, commentVo);

        //查作者信息：根据comment表中的authorId去sysUser表中拿作者信息
        Long authorId = comment.getAuthorId();
        UserVo userVo = sysUserService.findUserVoById(authorId);
        commentVo.setAuthor(userVo);

        //查子评论
        Integer level = comment.getLevel();
        if (1 == level) {
            Long id = comment.getId();
            List<CommentVo> commentVoList = findCommentsByParentId(id);
            commentVo.setChildren(commentVoList);
        }
        //查toUid 给谁评论 只有第二层的才有toUid，第一层是给文章评论的toUid=null
        if (level > 1) {
            Long toUid = comment.getToUid();
            UserVo toUserVo = sysUserService.findUserVoById(toUid);
            commentVo.setToUser(toUserVo);
        }
        return commentVo;
    }

    /**
     * 根据第一层评论id，查子评论
     */
    private List<CommentVo> findCommentsByParentId(Long id) {

        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId, id);
        queryWrapper.eq(Comment::getLevel, 2);
        List<Comment> commentList = commentMapper.selectList(queryWrapper);
        return copyList(commentList);
    }
}
