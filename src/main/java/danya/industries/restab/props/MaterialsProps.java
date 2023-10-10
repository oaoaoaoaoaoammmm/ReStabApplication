package danya.industries.restab.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "props.materials")
public class MaterialsProps {
    private int pageSize;
}
