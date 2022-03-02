package zn.blog.vo;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 给前端返回的json
 */

@Data
//生成含有所有字段的构造函数
@AllArgsConstructor
public class Result {

    private boolean success;

    private int code;

    private String msg;

    private Object data;

    public static Result success(Object data) {
        return new Result(true, 200, "success", data);
    }

    public static Result fail(int code, String msg) {
        return new Result(false, code, msg, null);
    }
}
