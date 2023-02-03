package ch.coop.hocl1.weatherapp.exception.types;

public class BackendDataInvalidException extends RuntimeException {

    public BackendDataInvalidException(Throwable e) {
        super("Backend data was invalid", e);
    }

}
