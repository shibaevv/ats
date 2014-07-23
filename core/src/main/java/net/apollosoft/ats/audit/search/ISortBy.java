package net.apollosoft.ats.audit.search;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public interface ISortBy
{
    /** ACTION */

    /** ACTION_BUSINESS_STATUS */
    String ACTION_BUSINESS_STATUS = "businessStatus_name";

    /** ACTION_DUE_DATE */
    String ACTION_CLOSED_DATE = "closedDate";

    /** ACTION_DUE_DATE_SQL */
    String ACTION_CLOSED_DATE_SQL = "action_closed_date";

    /** ACTION_DUE_DATE */
    String ACTION_DUE_DATE = "dueDate";

    /** ACTION_DUE_DATE_SQL */
    String ACTION_DUE_DATE_SQL = "action_due_date";

    /** ACTION_LIST_INDEX */
    String ACTION_LIST_INDEX = "listIndex";

    /** ACTION_TOTAL */
    String ACTION_TOTAL = "actionTotal";

    /** ACTION_OPEN */
    String ACTION_OPEN = "actionOpen";

    /** AUDIT */

    /** AUDIT_ISSUED_DATE */
    String AUDIT_ISSUED_DATE = "issuedDate";

    /** AUDIT_ISSUED_DATE_SQL */
    String AUDIT_ISSUED_DATE_SQL = "audit_issued_date";

    /** AUDIT_UPDATED_DATE */
    String AUDIT_UPDATED_DATE = "updatedDate";

    /** AUDIT_UPDATED_DATE_SQL */
    String AUDIT_UPDATED_DATE_SQL = "audit_updated_date";

    /** AUDIT_NAME */
    String AUDIT_NAME = "name";

    /** AUDIT_REPORT_TYPE */
    String AUDIT_REPORT_TYPE = "reportType";

    /** COMMENT */

    /** COMMENT_CREATED_BY property name */
    String COMMENT_CREATED_BY = "createdBy";

    /** COMMENT_CREATED_DATE property name */
    String COMMENT_CREATED_DATE = "createdDate";

    /** COMMENT_DETAIL property name */
    String COMMENT_DETAIL = "detail";

    /** COMMENT_LIST_INDEX */
    String COMMENT_LIST_INDEX = "listIndex";

    /** ISSUE */

    /** ISSUE_NAME */
    String ISSUE_NAME = "name";

    /** ISSUE_LIST_INDEX */
    String ISSUE_LIST_INDEX = "listIndex";

    /** OTHERS */

    /** DIVISION */
    String DIVISION_NAME = "division_name";

    /** GROUP */
    String GROUP_NAME = "group_name";

    /** USER_FULLNAME = 'firstName lastName' */
    String USER_FULLNAME = "user_firstName__user_lastName";

}