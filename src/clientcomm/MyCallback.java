package clientcomm;

public abstract class MyCallback<T> {
    public abstract void onSuccess(String result);

    public abstract void onFail();
}
