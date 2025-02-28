package io.github.alpha2303.pastenotes.pastenotes.utils;

public class Result<T> {
    protected T data;

    protected Exception error;

    protected boolean ok;

    protected Result(boolean isSuccess, T data, Exception error) {
        this.ok = isSuccess;
        this.data = data;
        this.error = error;
    }

    public static <T> Result<T> Ok(T data) {
        return new Result<>(true, data, null);
    }

    public static <T> Result<T> Err(Exception error) {
        return new Result<>(false, null, error);
    }

    public boolean isOk() {
        return this.ok;
    }

    public boolean isErr() {
        return !this.ok;
    }

    public T unwrap() throws Exception {
        if (!isOk()) {
            throw new Exception(String.format("Result.unwrap() - Failed to unwrap Result in Error State: %s", this.error.getMessage()));
        }
        return data;
    }
}
