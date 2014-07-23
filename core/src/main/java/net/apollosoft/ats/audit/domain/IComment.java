package net.apollosoft.ats.audit.domain;

import java.util.List;

import net.apollosoft.ats.domain.IAuditable;
import net.apollosoft.ats.domain.IDocument;

import org.apache.commons.lang.ArrayUtils;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public interface IComment extends IAuditable<Long>
{

    /** DETAIL property name */
    String NAME = "name";

    /** DETAIL property name */
    String DETAIL = "detail";

    /** LIST_INDEX property name */
    String LIST_INDEX = "listIndex";

    /** ACTION property name */
    String ACTION = "action";

    /** DOCUMENTS property name */
    String DOCUMENTS = "documents";

    /** IGNORE property(s) */
    String[] IGNORE = (String[]) ArrayUtils.addAll(IAuditable.IGNORE, new String[]
    {
        NAME, ACTION, DOCUMENTS
    });

    /**
     * @return id
     */
    Long getCommentId();

    /**
     * @return the auditLog
     */
    boolean isAuditLog();

    /**
     * @return the listIndex
     */
    Byte getListIndex();

    /**
     * @return the name
     */
    String getName();

    /**
     * @return the detail
     */
    String getDetail();

    /**
     * @return
     */
    IAction getAction();

    /**
     * @return the documents
     */
    List<IDocument> getDocuments();

}