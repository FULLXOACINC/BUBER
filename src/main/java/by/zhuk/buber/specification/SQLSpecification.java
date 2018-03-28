package by.zhuk.buber.specification;

import java.util.List;

public interface SQLSpecification extends Specification {

    String takePrepareQuery();

    List<Object> getPrepareParameters();
}