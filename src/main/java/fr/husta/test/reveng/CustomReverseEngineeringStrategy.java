package fr.husta.test.reveng;

import org.hibernate.cfg.reveng.DelegatingReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.ReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.TableIdentifier;

public class CustomReverseEngineeringStrategy
        extends DelegatingReverseEngineeringStrategy
        implements ReverseEngineeringStrategy {

    private static final String DTO_CLASS_SUFFIX = "DTO";

    public CustomReverseEngineeringStrategy(ReverseEngineeringStrategy delegate) {
        super(delegate);
    }

    @Override
    public String tableToClassName(TableIdentifier tableIdentifier) {
        return super.tableToClassName(tableIdentifier) + DTO_CLASS_SUFFIX;
    }

}
