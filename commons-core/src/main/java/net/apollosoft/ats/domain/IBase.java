package net.apollosoft.ats.domain;

/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public interface IBase<T>
{

    /** ID property name */
    String ID = "id";

    /** IGNORE property(s) */
    String[] IGNORE = new String[]
    {};

    /**
     * @return
     */
    T getId();

}