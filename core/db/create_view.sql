use ATS;

-- http://www.sqlteam.com/article/indexed-views-in-sql-server-2000

IF sessionproperty('ARITHABORT') = 0 SET ARITHABORT ON
GO
IF sessionproperty('CONCAT_NULL_YIELDS_NULL') = 0 SET CONCAT_NULL_YIELDS_NULL ON
GO
IF sessionproperty('QUOTED_IDENTIFIER') = 0 SET QUOTED_IDENTIFIER ON
GO
IF sessionproperty('ANSI_NULLS') = 0 SET ANSI_NULLS ON
GO
IF sessionproperty('ANSI_PADDING') = 0 SET ANSI_PADDING ON
GO
IF sessionproperty('ANSI_WARNINGS') = 0 SET ANSI_WARNINGS ON
GO
IF sessionproperty('NUMERIC_ROUNDABORT') = 1 SET NUMERIC_ROUNDABORT OFF
GO

CREATE VIEW v_ats_action
-- WITH SCHEMABINDING 
AS
SELECT     
           ats_audit.audit_id v_audit_id,
           ats_audit.audit_name audit_name,
           ats_audit.report_type_id v_report_type_id,
           ats_audit.audit_issued_date audit_issued_date,
           ats_audit.published_by v_audit_published_by,
           ats_audit.published_date v_audit_published_date,
           ats_issue.issue_id v_issue_id,
           ats_issue.issue_name issue_name, 
           ats_issue.rating_id v_rating_id,
           ats_rating.rating_name rating_name,
           ats_issue.published_by v_issue_published_by,
           ats_issue.published_date v_issue_published_date,
           ats_action.action_id v_action_id,
           ats_action.action_list_index action_list_index,
           ats_action.action_detail action_detail,
           ats_action.action_due_date action_due_date,
           ats_action.business_status_id v_business_status_id,
           ats_business_status.business_status_name business_status_name, 
           ats_business_status.action_status_id v_action_status_id,
           ats_action.action_closed_date action_closed_date,
           ats_action.published_by v_action_published_by,
           ats_action.published_date v_action_published_date,
           dbo.f_ats_action_last_comment(ats_action.action_id) action_last_comment
FROM dbo.ats_action LEFT OUTER JOIN
           dbo.ats_business_status ON ats_action.business_status_id = ats_business_status.business_status_id RIGHT OUTER JOIN
           dbo.ats_issue LEFT OUTER JOIN
           dbo.ats_rating ON ats_issue.rating_id = ats_rating.rating_id ON ats_action.issue_id = ats_issue.issue_id RIGHT OUTER JOIN
           dbo.ats_audit ON ats_issue.audit_id = ats_audit.audit_id
WHERE     (ats_audit.deleted_date IS NULL) AND (ats_issue.deleted_date IS NULL) AND (ats_action.deleted_date IS NULL)
GO

-- DROP  VIEW v_ats_action_export;
CREATE VIEW v_ats_action_export
-- WITH SCHEMABINDING 
AS
SELECT     
    ats_audit.audit_id v_audit_id,
    ats_audit.audit_name audit_name,
    ats_audit.audit_issued_date audit_issued_date,
    ats_report_type.report_type_id v_report_type_id,
    ats_report_type.report_type_name report_type_name,
    dbo.f_ats_audit_group_division(ats_audit.audit_id) audit_group_division,
    dbo.f_ats_audit_borm(ats_audit.audit_id) ats_audit_borm,
    ats_audit.published_by v_audit_published_by,
    ats_audit.published_date v_audit_published_date,
    ats_issue.issue_id v_issue_id,
    ats_issue.issue_list_index issue_list_index,
    ats_issue.issue_name issue_name, 
    ats_issue.issue_detail issue_detail,
    ats_issue.rating_id v_rating_id,
    ats_rating.rating_name rating_name,
    ats_parent_risk_category.parent_risk_category_name parent_risk_category_name,
    ats_parent_risk.parent_risk_name parent_risk_name,
    ats_issue.published_by v_issue_published_by,
    ats_issue.published_date v_issue_published_date,
    ats_action.action_id v_action_id,
    ats_action.action_list_index action_list_index,
    ats_action.action_detail action_detail,
    dbo.f_ats_action_responsible(ats_action.action_id) action_responsible,
    dbo.f_ats_action_group_division(ats_action.action_id) action_group_division,
    dbo.f_ats_action_borm(ats_action.action_id) action_borm,
    ats_action.action_due_date action_due_date,
    ats_action.business_status_id v_business_status_id,
    ats_business_status.business_status_name business_status_name, 
    ats_action.action_closed_date action_closed_date,
    dbo.f_ats_action_last_comment(ats_action.action_id) action_last_comment,
    dbo.f_ats_action_last_comment_user(ats_action.action_id) action_last_comment_user,
    dbo.f_ats_action_last_comment_date(ats_action.action_id) action_last_comment_date,
    ats_action_followup_status.action_followup_status_name action_followup_status_name,
    ats_action.action_followup_date action_followup_date,
    ats_action.published_by v_action_published_by,
    ats_action.published_date v_action_published_date
FROM dbo.ats_action LEFT OUTER JOIN
    dbo.ats_action_followup_status ON ats_action.action_followup_status_id = ats_action_followup_status.action_followup_status_id LEFT OUTER JOIN
    dbo.ats_business_status ON ats_action.business_status_id = ats_business_status.business_status_id RIGHT OUTER JOIN
    dbo.ats_parent_risk_category INNER JOIN
    dbo.ats_parent_risk ON ats_parent_risk_category.parent_risk_category_id = ats_parent_risk.parent_risk_category_id RIGHT OUTER JOIN
    dbo.ats_issue ON ats_parent_risk.parent_risk_id = ats_issue.parent_risk_id LEFT OUTER JOIN
    dbo.ats_rating ON ats_issue.rating_id = ats_rating.rating_id ON ats_action.issue_id = ats_issue.issue_id RIGHT OUTER JOIN
    dbo.ats_audit ON ats_issue.audit_id = ats_audit.audit_id LEFT OUTER JOIN
    dbo.ats_report_type ON ats_audit.report_type_id = ats_report_type.report_type_id
WHERE
    (ats_audit.deleted_date IS NULL) AND (ats_issue.deleted_date IS NULL) AND (ats_action.deleted_date IS NULL)
    AND (ats_audit.published_by IS NOT NULL OR ats_audit.published_date IS NOT NULL)
GO

-- DROP VIEW v_ats_audit
CREATE VIEW v_ats_audit
-- WITH SCHEMABINDING 
AS
SELECT     
           a.audit_id v_audit_id,
           a.audit_name audit_name,
           a.report_type_id v_report_type_id,
           a.audit_issued_date audit_issued_date,
           a.updated_date audit_updated_date,
           a.published_by v_audit_published_by,
           a.published_date v_audit_published_date,
           dbo.f_ats_audit_group_division(a.audit_id) audit_group_division,
           dbo.f_ats_audit_actionOpen(a.audit_id) audit_actionOpen,
           dbo.f_ats_audit_actionTotal(a.audit_id) audit_actionTotal,
           dbo.f_ats_audit_issueUnpublished(a.audit_id) audit_issueUnpublished,
           dbo.f_ats_audit_actionUnpublished(a.audit_id) audit_actionUnpublished
FROM dbo.ats_audit a
WHERE     a.deleted_date IS NULL
GO

-- select * from v_ats_audit;