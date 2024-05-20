package za.co.mafsoft.fault.tolerance.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coffee {
    private Integer id;
    private String name;
    private String countryOfOrigin;
    private Integer price;
}