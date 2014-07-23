use ATS;

DROP VIEW v_ats_action;
DROP VIEW v_ats_action_export;
DROP VIEW v_ats_audit;
DROP FUNCTION f_ats_audit_group_division;
DROP FUNCTION f_ats_audit_borm;
DROP FUNCTION f_ats_action_group_division;
DROP FUNCTION f_ats_action_borm;
DROP FUNCTION f_ats_action_responsible;
DROP FUNCTION f_ats_action_last_comment;
DROP FUNCTION f_ats_action_last_comment_user;
DROP FUNCTION f_ats_action_last_comment_date;
DROP FUNCTION f_ats_audit_actionOpen;
DROP FUNCTION f_ats_audit_actionTotal;
DROP FUNCTION f_ats_audit_issueUnpublished;
DROP FUNCTION f_ats_audit_actionUnpublished;

DROP TABLE ats_action_tag;
DROP TABLE ats_issue_tag;
DROP TABLE ats_audit_tag;
DROP TABLE ats_tag;
DROP TABLE ats_category_type;

DROP TABLE ats_comment_document;
DROP TABLE ats_comment;
DROP TABLE ats_action_responsible;
DROP TABLE ats_action_group_division;
DROP TABLE ats_action;
DROP TABLE ats_issue;
DROP TABLE ats_audit_group_division;
DROP TABLE ats_audit;
DROP TABLE ats_document;
DROP TABLE ats_template;

-- role based access
DROP TABLE ats_role_function;
DROP TABLE ats_function;

-- user data
DROP TABLE ats_user_group_division;
DROP TABLE ats_user_matrix;
DROP TABLE ats_user;
DROP TABLE ats_division;
DROP TABLE ats_group;
DROP TABLE ats_role;

-- reference data
DROP TABLE ats_user_type;
DROP TABLE ats_business_status;
DROP TABLE ats_action_status;
DROP TABLE ats_action_followup_status;
DROP TABLE ats_rating;
DROP TABLE ats_report_type;
DROP TABLE ats_parent_risk;
DROP TABLE ats_parent_risk_category;

DROP TABLE ats_dbversion;
