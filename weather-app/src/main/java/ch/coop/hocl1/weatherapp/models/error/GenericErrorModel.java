package ch.coop.hocl1.weatherapp.models.error;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GenericErrorModel {

    private String errorType;
    private String message;

}
