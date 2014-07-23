use ATS;

-- exec sp_password 'old_password', 'new_password', 'login';


-- http://www.sqlteam.com/article/indexed-views-in-sql-server-2000
-- Make sure that all of the session settings are set properly
IF sessionproperty('ARITHABORT') = 0 SET ARITHABORT ON;
IF sessionproperty('CONCAT_NULL_YIELDS_NULL') = 0 SET CONCAT_NULL_YIELDS_NULL ON;
IF sessionproperty('QUOTED_IDENTIFIER') = 0 SET QUOTED_IDENTIFIER ON;
IF sessionproperty('ANSI_NULLS') = 0 SET ANSI_NULLS ON;
IF sessionproperty('ANSI_PADDING') = 0 SET ANSI_PADDING ON;
IF sessionproperty('ANSI_WARNINGS') = 0 SET ANSI_WARNINGS ON;
IF sessionproperty('NUMERIC_ROUNDABORT') = 1 SET NUMERIC_ROUNDABORT OFF;


-- drop table ats_action;
create table ats_action (
    action_id numeric(19,0) not null identity(10001, 1),
    issue_id numeric(19,0) not null,
    business_status_id numeric(19,0),
    action_followup_status_id numeric(19,0),
    action_followup_date datetime,
    action_list_index numeric(5,2),
    action_ref varchar(20) null,
    action_name varchar(254) null,
    action_detail text null,
    action_due_date datetime null,
    action_closed_date datetime null,
    -- more
    published_by varchar(8),
    published_date datetime,
    created_by varchar(8),
    created_date datetime,
    updated_by varchar(8),
    updated_date datetime,
    deleted_by varchar(8),
    deleted_date datetime,
    lock_version numeric(19, 0) not null default 0,
    constraint pk_ats_action primary key (action_id)
);

-- drop table ats_action_followup_status;
create table ats_action_followup_status (
    action_followup_status_id numeric(19,0) not null,
    action_followup_status_name varchar(254) not null,
    created_by varchar(8),
    created_date datetime,
    updated_by varchar(8),
    updated_date datetime,
    deleted_by varchar(8),
    deleted_date datetime,
    lock_version numeric(19, 0) not null default 0,
    constraint pk_ats_action_followup_status primary key (action_followup_status_id)
);

-- drop table ats_action_group_division;
create table ats_action_group_division (
    action_group_division_id numeric(19,0) not null identity(10001, 1),
    action_id numeric(19,0) not null,
    group_id numeric(19,0) not null,
    division_id numeric(19,0) null,
    created_by varchar(8),
    created_date datetime,
    updated_by varchar(8),
    updated_date datetime,
    deleted_by varchar(8),
    deleted_date datetime,
    lock_version numeric(19, 0) not null default 0,
    constraint pk_ats_action_group_division primary key (action_group_division_id)
);

-- drop table ats_action_responsible;
create table ats_action_responsible (
    action_id numeric(19,0) not null,
    user_id varchar(8) not null,
    created_by varchar(8),
    created_date datetime,
    updated_by varchar(8),
    updated_date datetime,
    deleted_by varchar(8),
    deleted_date datetime,
    lock_version numeric(19, 0) not null default 0,
    constraint pk_ats_action_responsible primary key (action_id, user_id)
);

-- drop table ats_action_status;
create table ats_action_status (
    action_status_id numeric(19,0) not null,
    action_status_name varchar(254) not null,
    created_by varchar(8),
    created_date datetime,
    updated_by varchar(8),
    updated_date datetime,
    deleted_by varchar(8),
    deleted_date datetime,
    lock_version numeric(19, 0) not null default 0,
    constraint pk_ats_action_status primary key (action_status_id)
);

-- drop table ats_audit;
create table ats_audit (
    audit_id numeric(19,0) not null identity(10001, 1),
    report_type_id numeric(19,0) null,
    audit_ref varchar(20) null,
    audit_name varchar(254) not null,
    audit_detail text null,
    audit_issued_date datetime null,
    document_id numeric(19,0) null,
    audit_source char(1) null, -- G (Galileo), O (Orr) or null (iad)
    -- more
    published_by varchar(8),
    published_date datetime,
    created_by varchar(8),
    created_date datetime,
    updated_by varchar(8),
    updated_date datetime,
    deleted_by varchar(8),
    deleted_date datetime,
    lock_version numeric(19, 0) not null default 0,
    constraint pk_ats_audit primary key (audit_id)
);

-- drop table ats_audit_group_division;
create table ats_audit_group_division (
    audit_group_division_id numeric(19,0) not null identity(10001, 1),
    audit_id numeric(19,0) not null,
    group_id numeric(19,0) not null,
    division_id numeric(19,0) null,
    created_by varchar(8),
    created_date datetime,
    updated_by varchar(8),
    updated_date datetime,
    deleted_by varchar(8),
    deleted_date datetime,
    lock_version numeric(19, 0) not null default 0,
    constraint pk_ats_audit_group_division primary key (audit_group_division_id)
);

-- drop table ats_business_status;
create table ats_business_status (
    business_status_id numeric(19,0) not null,
    business_status_name varchar(254) not null,
    action_status_id numeric(19,0),
    created_by varchar(8),
    created_date datetime,
    updated_by varchar(8),
    updated_date datetime,
    deleted_by varchar(8),
    deleted_date datetime,
    lock_version numeric(19, 0) not null default 0,
    constraint pk_ats_business_status primary key (business_status_id)
);

-- drop table ats_comment;
create table ats_comment (
    comment_id numeric(19,0) not null identity(10001, 1),
    action_id numeric(19,0) not null,
    comment_list_index numeric(3,0),
    comment_detail text not null,
    audit_log char(1) not null default 'N',
    -- more
    created_by varchar(8),
    created_date datetime,
    updated_by varchar(8),
    updated_date datetime,
    deleted_by varchar(8),
    deleted_date datetime,
    lock_version numeric(19, 0) not null default 0,
    constraint pk_ats_comment primary key (comment_id)
);

-- drop table ats_comment_document;
create table ats_comment_document (
    comment_id numeric(19,0) not null,
    document_id numeric(19,0) not null,
    created_by varchar(8),
    created_date datetime,
    updated_by varchar(8),
    updated_date datetime,
    deleted_by varchar(8),
    deleted_date datetime,
    lock_version numeric(19, 0) not null default 0,
    constraint pk_ats_comment_document primary key (comment_id, document_id)
);

-- =============================================================================
--    to keep track of db updates
--    version control    (xxxx.01.01)
--        xxxx - app code (e.g. mats)
--        01 - major version number
--        01 - minor version number
-- =============================================================================
-- drop table ats_dbversion;
create table ats_dbversion (
  dbversion_id varchar(20) not null,
  prev_dbversion_id varchar(20),
  created_date datetime not null default getdate(),
  constraint pk_ats_dbversion primary key (dbversion_id)
);

-- drop table ats_division;
create table ats_division (
    division_id   numeric(19,0) not null,
    division_name varchar(100)  not null,
    group_id      numeric(19,0) null,
    validEnd      datetime      null,
    validStart    datetime      null,
    created_by varchar(8),
    created_date datetime,
    updated_by varchar(8),
    updated_date datetime,
    deleted_by varchar(8),
    deleted_date datetime,
    lock_version numeric(19, 0) not null default 0,
    constraint pk_ats_division primary key (division_id)
);

-- drop table ats_document;
create table ats_document (
    document_id numeric(19,0) not null identity(10001, 1),
    document_ref varchar(20) null,
    document_name varchar(254) not null,
    document_detail text null,
    document_content image not null,
    document_content_type varchar(200) not null,
    -- more
    created_by varchar(8),
    created_date datetime,
    updated_by varchar(8),
    updated_date datetime,
    deleted_by varchar(8),
    deleted_date datetime,
    lock_version numeric(19, 0) not null default 0,
    constraint pk_ats_document primary key (document_id)
);

-- drop table ats_function;
create table ats_function (
    function_id numeric(19,0) not null identity(10001, 1),
    function_name varchar(254) not null,
    function_detail text null,
    function_module varchar(50) not null,
    function_query varchar(254) not null,
    home char(1) not null default 'N',
    created_by varchar(8),
    created_date datetime,
    updated_by varchar(8),
    updated_date datetime,
    deleted_by varchar(8),
    deleted_date datetime,
    lock_version numeric(19, 0) not null default 0,
    constraint pk_ats_function primary key (function_id)
);

-- drop table ats_group;
create table ats_group (
    group_id      numeric(19,0) not null,
    group_name    varchar(100)  not null,
    validEnd      datetime      null,
    validStart    datetime      null,
    created_by varchar(8),
    created_date datetime,
    updated_by varchar(8),
    updated_date datetime,
    deleted_by varchar(8),
    deleted_date datetime,
    lock_version numeric(19, 0) not null default 0,
    constraint pk_ats_group primary key (group_id)
);

-- drop table ats_issue;
create table ats_issue (
    issue_id numeric(19,0) not null identity(10001, 1),
    audit_id numeric(19,0) not null,
    parent_risk_id numeric(19,0),
    rating_id numeric(19,0) null,
    issue_list_index numeric(3,0),
    issue_ref varchar(20) null,
    issue_name varchar(254) null,
    issue_detail text null,
    -- more
    published_by varchar(8),
    published_date datetime,
    created_by varchar(8),
    created_date datetime,
    updated_by varchar(8),
    updated_date datetime,
    deleted_by varchar(8),
    deleted_date datetime,
    lock_version numeric(19, 0) not null default 0,
    constraint pk_ats_issue primary key (issue_id)
);

-- drop table ats_parent_risk_category;
create table ats_parent_risk_category (
    parent_risk_category_id numeric(19,0) not null,
    parent_risk_category_name varchar(254) not null,
    created_by varchar(8),
    created_date datetime,
    updated_by varchar(8),
    updated_date datetime,
    deleted_by varchar(8),
    deleted_date datetime,
    lock_version numeric(19, 0) not null default 0,
    constraint pk_ats_parent_risk_category primary key (parent_risk_category_id)
);

-- drop table ats_parent_risk;
create table ats_parent_risk (
    parent_risk_id numeric(19,0) not null,
    parent_risk_name varchar(254) not null,
    parent_risk_category_id numeric(19,0) not null,
    created_by varchar(8),
    created_date datetime,
    updated_by varchar(8),
    updated_date datetime,
    deleted_by varchar(8),
    deleted_date datetime,
    lock_version numeric(19, 0) not null default 0,
    constraint pk_ats_parent_risk primary key (parent_risk_id)
);

-- drop table ats_rating;
create table ats_rating (
    rating_id numeric(19,0) not null,
    rating_name varchar(254) not null,
    created_by varchar(8),
    created_date datetime,
    updated_by varchar(8),
    updated_date datetime,
    deleted_by varchar(8),
    deleted_date datetime,
    lock_version numeric(19, 0) not null default 0,
    constraint pk_ats_rating primary key (rating_id)
);

-- drop table report_type;
create table ats_report_type (
    report_type_id numeric(19,0) not null,
    report_type_name varchar(254) not null,
    created_by varchar(8),
    created_date datetime,
    updated_by varchar(8),
    updated_date datetime,
    deleted_by varchar(8),
    deleted_date datetime,
    lock_version numeric(19, 0) not null default 0,
    constraint pk_ats_report_type primary key (report_type_id)
);

-- drop table ats_role;
create table ats_role (
    role_id numeric(19,0) not null,
    role_name varchar(254) not null,
    role_detail text null,
    role_priority numeric(3,0) not null,
    created_by varchar(8),
    created_date datetime,
    updated_by varchar(8),
    updated_date datetime,
    deleted_by varchar(8),
    deleted_date datetime,
    lock_version numeric(19, 0) not null default 0,
    constraint pk_ats_role primary key (role_id)
);

-- drop table ats_role_function;
create table ats_role_function (
    role_id numeric(19,0) not null,
    function_id numeric(19,0) not null,
    home char(1) not null default 'N',
    created_by varchar(8),
    created_date datetime,
    updated_by varchar(8),
    updated_date datetime,
    deleted_by varchar(8),
    deleted_date datetime,
    lock_version numeric(19, 0) not null default 0,
    constraint pk_ats_role_function primary key (role_id, function_id)
);

-- drop table ats_template;
create table ats_template (
    template_id numeric(19,0) not null identity(10001, 1),
    template_ref varchar(20) not null,
    template_name varchar(254) not null,
    template_detail text null,
    template_content image not null,
    template_content_type varchar(200) not null,
    -- more
    created_by varchar(8),
    created_date datetime,
    updated_by varchar(8),
    updated_date datetime,
    deleted_by varchar(8),
    deleted_date datetime,
    lock_version numeric(19, 0) not null default 0,
    constraint pk_ats_template primary key (template_id)
);

-- drop table ats_user;
create table ats_user (
    id                varchar(8)  not null,
    title             varchar(30) not null,
    last_name         varchar(25) not null,
    first_name        varchar(25) not null,
    other_names       varchar(25) null,
    userid            varchar(8)  not null,
    termination_date  datetime    null, -- YYYYMMDD
    status            char(1)     null,
    created_by varchar(8),
    created_date datetime,
    updated_by varchar(8),
    updated_date datetime,
    deleted_by varchar(8),
    deleted_date datetime,
    lock_version numeric(19, 0) not null default 0,
    constraint pk_ats_user primary key (id)
);

-- drop table ats_user_group_division;
create table ats_user_group_division (
    user_group_division_id numeric(19,0) not null identity(10001, 1),
    user_id varchar(8) not null,
    group_id numeric(19,0) not null,
    division_id numeric(19,0) null,
    user_type_id numeric(19,0) not null,
    start_date datetime null,
    end_date datetime null,
    created_by varchar(8),
    created_date datetime,
    updated_by varchar(8),
    updated_date datetime,
    deleted_by varchar(8),
    deleted_date datetime,
    lock_version numeric(19, 0) not null default 0,
    constraint pk_ats_user_group_division primary key (user_group_division_id)
);

-- drop table ats_user_matrix;
create table ats_user_matrix (
    user_matrix_id numeric(19,0) not null identity(10001, 1),
    user_id varchar(8) not null,
    role_id numeric(19,0) not null,
    report_type_id numeric(19,0) not null,
    group_id numeric(19,0) null,
    division_id numeric(19,0) null,
    created_by varchar(8),
    created_date datetime,
    updated_by varchar(8),
    updated_date datetime,
    deleted_by varchar(8),
    deleted_date datetime,
    lock_version numeric(19, 0) not null default 0,
    constraint pk_ats_user_matrix primary key (user_matrix_id)
);

-- drop table ats_user_type;
create table ats_user_type (
    user_type_id numeric(19,0) not null,
    user_type_name varchar(254) not null,
    created_by varchar(8),
    created_date datetime,
    updated_by varchar(8),
    updated_date datetime,
    deleted_by varchar(8),
    deleted_date datetime,
    lock_version numeric(19, 0) not null default 0,
    constraint pk_ats_user_type primary key (user_type_id)
);


alter table ats_action add constraint fk_ats_action_1 foreign key (issue_id) references ats_issue (issue_id);
alter table ats_action add constraint fk_ats_action_2 foreign key (action_followup_status_id) references ats_action_followup_status (action_followup_status_id);
alter table ats_action add constraint fk_ats_action_3 foreign key (business_status_id) references ats_business_status (business_status_id);
create index i_ats_action_1 on ats_action(issue_id);
create index i_ats_action_2 on ats_action(business_status_id);
create index i_ats_action_3 on ats_action(action_ref);
create index i_ats_action_4 on ats_action(action_name);
create index i_ats_action_5 on ats_action(action_due_date);
create index i_ats_action_6 on ats_action(action_closed_date);
create index i_ats_action_7 on ats_action(action_followup_status_id);
create index i_ats_action_8 on ats_action(action_list_index);

alter table ats_action_group_division add constraint fk_ats_action_group_division_1 foreign key (action_id) references ats_action (action_id);
alter table ats_action_group_division add constraint fk_ats_action_group_division_2 foreign key (group_id) references ats_group (group_id);
alter table ats_action_group_division add constraint fk_ats_action_group_division_3 foreign key (division_id) references ats_division (division_id);

alter table ats_action_responsible add constraint fk_ats_action_responsible_1 foreign key (action_id) references ats_action (action_id);
alter table ats_action_responsible add constraint fk_ats_action_responsible_2 foreign key (user_id) references ats_user (id);

alter table ats_audit add constraint fk_ats_audit_1 foreign key (document_id) references ats_document (document_id);
alter table ats_audit add constraint fk_ats_audit_2 foreign key (report_type_id) references ats_report_type (report_type_id);
create index i_ats_audit_1 on ats_audit(audit_ref);
create index i_ats_audit_2 on ats_audit(audit_name);
create index i_ats_audit_3 on ats_audit(audit_issued_date);
create index i_ats_audit_4 on ats_audit(audit_source);
create index i_ats_audit_5 on ats_audit(report_type_id);

alter table ats_audit_group_division add constraint fk_ats_audit_group_division_1 foreign key (audit_id) references ats_audit (audit_id);
alter table ats_audit_group_division add constraint fk_ats_audit_group_division_2 foreign key (group_id) references ats_group (group_id);
alter table ats_audit_group_division add constraint fk_ats_audit_group_division_3 foreign key (division_id) references ats_division (division_id);

alter table ats_business_status add constraint fk_ats_business_status_1 foreign key (action_status_id) references ats_action_status (action_status_id);

alter table ats_comment add constraint fk_ats_comment_1 foreign key (action_id) references ats_action (action_id);
-- alter table ats_comment add constraint u_ats_comment_1 unique (action_id, comment_detail);
create index i_ats_comment_1 on ats_comment(action_id);
create index i_ats_comment_2 on ats_comment(comment_list_index);

alter table ats_comment_document add constraint fk_ats_comment_document_1 foreign key (comment_id) references ats_comment (comment_id);
alter table ats_comment_document add constraint fk_ats_comment_document_2 foreign key (document_id) references ats_document (document_id);

alter table ats_dbversion add constraint fk_ats_dbversion_1 foreign key (prev_dbversion_id) references ats_dbversion (dbversion_id);

alter table ats_division add constraint fk_ats_division_1 foreign key (group_id) references ats_group (group_id);
create index i_ats_division_1 on ats_division(group_id);

alter table ats_issue add constraint fk_ats_issue_1 foreign key (audit_id) references ats_audit (audit_id);
alter table ats_issue add constraint fk_ats_issue_2 foreign key (rating_id) references ats_rating (rating_id);
alter table ats_issue add constraint fk_ats_issue_3 foreign key (parent_risk_id) references ats_parent_risk (parent_risk_id);
create index i_ats_issue_1 on ats_issue(audit_id);
create index i_ats_issue_2 on ats_issue(parent_risk_id);
create index i_ats_issue_3 on ats_issue(rating_id);
create index i_ats_issue_4 on ats_issue(issue_list_index);
create index i_ats_issue_5 on ats_issue(issue_ref);
create index i_ats_issue_6 on ats_issue(issue_name);

alter table ats_parent_risk add constraint fk_ats_parent_risk_1 foreign key (parent_risk_category_id) references ats_parent_risk_category (parent_risk_category_id);

alter table ats_rating add constraint u_ats_rating_1 unique (rating_name);

alter table ats_role_function add constraint fk_ats_role_function_1 foreign key (role_id) references ats_role (role_id);
alter table ats_role_function add constraint fk_ats_role_function_2 foreign key (function_id) references ats_function (function_id);

alter table ats_template add constraint u_ats_template_1 unique (template_ref);

create nonclustered index i_ats_user_1 on ats_user(userid);
create nonclustered index i_ats_user_2 on ats_user(first_name);
create nonclustered index i_ats_user_3 on ats_user(last_name);
create nonclustered index i_ats_user_4 on ats_user(first_name, last_name);

alter table ats_user_group_division add constraint fk_ats_user_group_division_1 foreign key (user_id) references ats_user (id);
alter table ats_user_group_division add constraint fk_ats_user_group_division_2 foreign key (group_id) references ats_group (group_id);
alter table ats_user_group_division add constraint fk_ats_user_group_division_3 foreign key (division_id) references ats_division (division_id);
alter table ats_user_group_division add constraint fk_ats_user_group_division_4 foreign key (user_type_id) references ats_user_type (user_type_id);

alter table ats_user_matrix add constraint fk_ats_user_matrix_1 foreign key (user_id) references ats_user (id);
alter table ats_user_matrix add constraint fk_ats_user_matrix_2 foreign key (role_id) references ats_role (role_id);
alter table ats_user_matrix add constraint fk_ats_user_matrix_3 foreign key (report_type_id) references ats_report_type (report_type_id);
alter table ats_user_matrix add constraint fk_ats_user_matrix_4 foreign key (group_id) references ats_group (group_id);
alter table ats_user_matrix add constraint fk_ats_user_matrix_5 foreign key (division_id) references ats_division (division_id);
alter table ats_user_matrix add constraint u_ats_user_matrix_1 unique (user_id, role_id, report_type_id, group_id, division_id);


-- drop table ats_audit_tag;
create table ats_audit_tag (
    audit_id numeric(19,0) not null,
    tag_id numeric(19,0) not null,
    constraint pk_ats_audit_tag primary key (audit_id, tag_id)
);

-- drop table ats_issue_tag;
create table ats_issue_tag (
    issue_id numeric(19,0) not null,
    tag_id numeric(19,0) not null,
    constraint pk_ats_issue_tag primary key (issue_id, tag_id)
);

-- drop table ats_action_tag;
create table ats_action_tag (
    action_id numeric(19,0) not null,
    tag_id numeric(19,0) not null,
    constraint pk_ats_action_tag primary key (action_id, tag_id)
);

-- drop table category_type;
create table ats_category_type (
    category_type_id numeric(19,0) not null identity(1, 1),
    category_type_name varchar(100) not null,
    category_type_detail varchar(254) null,
    created_by varchar(8),
    created_date datetime,
    updated_by varchar(8),
    updated_date datetime,
    deleted_by varchar(8),
    deleted_date datetime,
    lock_version numeric(19, 0) not null default 0,
    constraint pk_ats_category_type primary key (category_type_id)
);

-- drop table ats_tag;
create table ats_tag (
    tag_id numeric(19,0) not null identity(10001, 1),
    category_type_id numeric(19,0) not null,
    category_value varchar(250) not null,
    created_by varchar(8),
    created_date datetime,
    updated_by varchar(8),
    updated_date datetime,
    deleted_by varchar(8),
    deleted_date datetime,
    lock_version numeric(19, 0) not null default 0,
    constraint pk_ats_tag primary key (tag_id)
);

alter table ats_audit_tag add constraint fk_ats_audit_tag_1 foreign key (audit_id) references ats_audit (audit_id);
alter table ats_audit_tag add constraint fk_ats_audit_tag_2 foreign key (tag_id) references ats_tag (tag_id);

alter table ats_issue_tag add constraint fk_ats_issue_tag_1 foreign key (issue_id) references ats_issue (issue_id);
alter table ats_issue_tag add constraint fk_ats_issue_tag_2 foreign key (tag_id) references ats_tag (tag_id);

alter table ats_action_tag add constraint fk_ats_action_tag_1 foreign key (action_id) references ats_action (action_id);
alter table ats_action_tag add constraint fk_ats_action_tag_2 foreign key (tag_id) references ats_tag (tag_id);

alter table ats_tag add constraint fk_ats_tag_1 foreign key (category_type_id) references ats_category_type (category_type_id);

create index i_ats_tag_1 on ats_tag(category_type_id);
create index i_ats_tag_2 on ats_tag(category_value);
