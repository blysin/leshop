package com.blysin.leshop.common.domain;


import com.blysin.leshop.common.constant.HttpStatusCodes;

import javax.servlet.http.HttpServletResponse;

/**
 * RestFul API 返回参数
 *
 * @author Blysin
 * @since 2017/8/3
 */
public class RestResult {

    private Object result;//20* 时返回的数据
    private String error;//40*、50*时返回的错误信息

    private static RestResult successResult = new RestResult();

    private RestResult() {
        this.result = "success";
    }

    /**
     * 普通成功
     *
     * @param response
     * @return
     */
    public static RestResult newSuccessResult(HttpServletResponse response) {
        response.setStatus(HttpStatusCodes.SC_OK);
        return successResult;
    }

    /**
     * 更新或创建成功 201
     *
     * @param response
     * @param result
     * @return
     */
    public static RestResult newCreateOrUpdateSuccess(HttpServletResponse response, Object result) {
        response.setStatus(HttpStatusCodes.SC_CREATED);
        return new RestResult(result);
    }

    /**
     * 删除成功 204
     *
     * @param response
     * @param result
     * @return
     */
    public static RestResult newDeleteSuccess(HttpServletResponse response, Object result) {
        response.setStatus(HttpStatusCodes.SC_NO_CONTENT);
        return new RestResult(result);
    }

    /**
     * 用户未登录错误 401
     *
     * @param response
     * @param message
     * @return
     */
    public static RestResult newUnauthorizedError(HttpServletResponse response, String message) {
        response.setStatus(HttpStatusCodes.SC_UNAUTHORIZED);
        return new RestResult(message);
    }

    /**
     * 权限不足错误 406
     *
     * @param response
     * @param message
     * @return
     */
    public static RestResult newAcceptableError(HttpServletResponse response, String message) {
        response.setStatus(HttpStatusCodes.SC_NOT_ACCEPTABLE);
        return new RestResult(message);
    }

    /**
     * 创建或更新数据错误 422
     *
     * @param response
     * @param message
     * @return
     */
    public static RestResult newUnprocessableError(HttpServletResponse response, String message) {
        response.setStatus(HttpStatusCodes.Unprocesable);
        return new RestResult(message);
    }

    /**
     * 资源冲突
     *
     * @param response
     * @param message
     * @return
     */
    public static RestResult newConflictError(HttpServletResponse response, String message) {
        response.setStatus(HttpStatusCodes.SC_CONFLICT);
        return new RestResult(message);
    }


    private RestResult(Object result) {
        this.result = result;
    }

    private RestResult(String error) {
        this.error = error;
    }

    /**
     * 200 OK - [GET]：服务器成功返回用户请求的数据，该操作是幂等的（Idempotent）。
     * 201 CREATED - [POST/PUT/PATCH]：用户新建或修改数据成功。
     * 202 Accepted - [*]：表示一个请求已经进入后台排队（异步任务）
     * 204 NO CONTENT - [DELETE]：用户删除数据成功。
     * 400 INVALID REQUEST - [POST/PUT/PATCH]：用户发出的请求有错误，服务器没有进行新建或修改数据的操作，该操作是幂等的。
     * 401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
     * 403 Forbidden - [*] 表示用户得到授权（与401错误相对），但是访问是被禁止的。
     * 404 NOT FOUND - [*]：用户发出的请求针对的是不存在的记录，服务器没有进行操作，该操作是幂等的。
     * 406 Not Acceptable - [GET]：用户请求的格式不可得（比如用户请求JSON格式，但是只有XML格式）。
     * 410 Gone -[GET]：用户请求的资源被永久删除，且不会再得到的。
     * 422 Unprocesable entity - [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。
     * 500 INTERNAL SERVER ERROR - [*]：服务器发生错误，用户将无法判断发出的请求是否成功。
     */
    public RestResult(HttpServletResponse response, Integer statusCodes, Object result) {
        response.setStatus(statusCodes);
        this.result = result;
    }

    public RestResult(HttpServletResponse response, Integer statusCodes, String error) {
        response.setStatus(statusCodes);
        this.error = error;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
