use ATS;

INSERT INTO ats_user (id,title,last_name,first_name,userid,status,created_date)
VALUES ('admin','Mr','ad','min','admin','A',getDate());

declare @created_by varchar(8);
set @created_by = 'admin';

insert into ats_action_followup_status (action_followup_status_id, action_followup_status_name, created_date) values (1, 'Open', getDate());
insert into ats_action_followup_status (action_followup_status_id, action_followup_status_name, created_date) values (2, 'Closed (Verified)', getDate());
insert into ats_action_followup_status (action_followup_status_id, action_followup_status_name, created_date) values (3, 'Closed (Accepted)', getDate());

insert into ats_action_status (action_status_id, action_status_name, created_date) values (1, 'Open', getDate());
insert into ats_action_status (action_status_id, action_status_name, created_date) values (2, 'Closed', getDate());

insert into ats_business_status (business_status_id, business_status_name, action_status_id, created_date) values (1, 'Not Actioned', 1, getDate());
insert into ats_business_status (business_status_id, business_status_name, action_status_id, created_date) values (2, 'In Progress', 1, getDate());
insert into ats_business_status (business_status_id, business_status_name, action_status_id, created_date) values (3, 'Implemented', 2, getDate());
insert into ats_business_status (business_status_id, business_status_name, action_status_id, created_date) values (4, 'No Longer Applicable', 2, getDate());

insert into ats_user_type (user_type_id, user_type_name, created_date) values (1, 'Owner', getDate());

insert into ats_group (group_id, group_name, created_date) values (9223372036854775807, 'Global', getDate());

insert into ats_rating (rating_id, rating_name, created_date) values (1, 'Very High', getDate());
insert into ats_rating (rating_id, rating_name, created_date) values (2, 'High', getDate());
insert into ats_rating (rating_id, rating_name, created_date) values (3, 'Medium', getDate());
insert into ats_rating (rating_id, rating_name, created_date) values (4, 'Low', getDate());
insert into ats_rating (rating_id, rating_name, created_date) values (5, 'Very Low', getDate());

insert into ats_report_type (report_type_id, report_type_name, created_date) values (1, 'Audit', getDate());
insert into ats_report_type (report_type_id, report_type_name, created_date) values (2, 'Compliance', getDate());

insert into ats_role (role_id, role_name, role_priority, created_date) values (1, 'System Admin', 127, getDate());
insert into ats_role (role_id, role_name, role_priority, created_date) values (2, 'Report Owner', 1, getDate());
insert into ats_role (role_id, role_name, role_priority, created_date) values (3, 'Report Team', 2, getDate());
insert into ats_role (role_id, role_name, role_priority, created_date) values (4, 'Oversight Team', 3, getDate());
insert into ats_role (role_id, role_name, role_priority, created_date) values (5, 'Read-only', 4, getDate());
insert into ats_role (role_id, role_name, role_priority, created_date) values (6, 'Default Role', 5, getDate());


INSERT INTO ats_user_matrix (user_id,role_id,report_type_id,created_date) VALUES ('admin',1,1,getDate());
INSERT INTO ats_user_matrix (user_id,role_id,report_type_id,created_date) VALUES ('admin',1,2,getDate());


-- clean up
-- delete from ats_user_group_division;
-- insert into ats_user_group_division (user_id,group_id,division_id,user_type_id,created_by,created_date) values ('',10,NULL,1,@created_by,getDate());

-- clean up
-- delete from ats_role_function;
-- delete from ats_function;

-- Root
declare @function_id numeric(19,0);

insert into ats_function (function_name,function_detail,function_module,function_query,created_by,created_date) values
 ('Login','Login page','','login.do',@created_by,getDate());
set @function_id = @@IDENTITY;
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (1,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (2,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (3,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (4,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (5,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (6,@function_id,'N',@created_by,getDate());

insert into ats_function (function_name,function_detail,function_module,function_query,created_by,created_date) values
 ('Main','Main page','','main.do',@created_by,getDate());
set @function_id = @@IDENTITY;
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (1,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (2,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (3,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (4,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (5,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (6,@function_id,'N',@created_by,getDate());

-- Filter
insert into ats_function (function_name,function_detail,function_module,function_query,created_by,created_date) values
 ('Filter','Filter','Filter','filter/*',@created_by,getDate());
set @function_id = @@IDENTITY;
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (1,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (2,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (3,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (4,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (5,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (6,@function_id,'N',@created_by,getDate());

-- Setup
insert into ats_function (function_name,function_detail,function_module,function_query,home,created_by,created_date) values
 ('System Admin','System Admin page','Setup','setup/main.do','Y',@created_by,getDate());
set @function_id = @@IDENTITY;
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (1,@function_id,'Y',@created_by,getDate());

insert into ats_function (function_name,function_detail,function_module,function_query,created_by,created_date) values
 ('System Admin pages','System Admin pages','Setup','setup/*',@created_by,getDate());
set @function_id = @@IDENTITY;
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (1,@function_id,'N',@created_by,getDate());

-- User
insert into ats_function (function_name,function_detail,function_module,function_query,home,created_by,created_date) values
 ('User Admin','User Admin Page','User','user/view.do','Y',@created_by,getDate());
set @function_id = @@IDENTITY;
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (1,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (2,@function_id,'N',@created_by,getDate());

insert into ats_function (function_name,function_detail,function_module,function_query,created_by,created_date) values
 ('User Admin pages','User Admin pages','User','user/*',@created_by,getDate());
set @function_id = @@IDENTITY;
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (1,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (2,@function_id,'N',@created_by,getDate());

-- Report
insert into ats_function (function_name,function_detail,function_module,function_query,home,created_by,created_date) values
 ('Search Reports','Search Reports page','Report','audit/main.do','Y',@created_by,getDate());
set @function_id = @@IDENTITY;
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (2,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (3,@function_id,'Y',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (4,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (5,@function_id,'N',@created_by,getDate());

insert into ats_function (function_name,function_detail,function_module,function_query,created_by,created_date) values
 ('Report View','Report View page','Report','audit/view.do',@created_by,getDate());
set @function_id = @@IDENTITY;
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (2,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (3,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (4,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (5,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (6,@function_id,'N',@created_by,getDate());

-- Issue
insert into ats_function (function_name,function_detail,function_module,function_query,created_by,created_date) values
 ('Issue Search','Issue Search page','Report','issue/main.do',@created_by,getDate());
set @function_id = @@IDENTITY;
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (2,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (3,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (4,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (5,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (6,@function_id,'N',@created_by,getDate());

insert into ats_function (function_name,function_detail,function_module,function_query,created_by,created_date) values
 ('Issue View','Issue View page','Report','issue/view.do',@created_by,getDate());
set @function_id = @@IDENTITY;
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (2,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (3,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (4,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (5,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (6,@function_id,'N',@created_by,getDate());

-- Action
insert into ats_function (function_name,function_detail,function_module,function_query,home,created_by,created_date) values
 ('Search Actions','Search Actions page','Report','action/main.do','Y',@created_by,getDate());
set @function_id = @@IDENTITY;
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (2,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (3,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (4,@function_id,'Y',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (5,@function_id,'Y',@created_by,getDate());

insert into ats_function (function_name,function_detail,function_module,function_query,created_by,created_date) values
 ('Action View','Action View page','Report','action/view.do',@created_by,getDate());
set @function_id = @@IDENTITY;
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (2,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (3,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (4,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (5,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (6,@function_id,'N',@created_by,getDate());

-- Comment
insert into ats_function (function_name,function_detail,function_module,function_query,created_by,created_date) values
 ('Comment List','Comment List page','Report','comment/main.do',@created_by,getDate());
set @function_id = @@IDENTITY;
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (2,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (3,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (4,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (5,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (6,@function_id,'N',@created_by,getDate());

insert into ats_function (function_name,function_detail,function_module,function_query,created_by,created_date) values
 ('Comment View','Comment View page','Report','comment/view.do',@created_by,getDate());
set @function_id = @@IDENTITY;
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (2,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (3,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (4,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (5,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (6,@function_id,'N',@created_by,getDate());

-- MyAction
insert into ats_function (function_name,function_detail,function_module,function_query,home,created_by,created_date) values
 ('My Actions','My Actions page','MyAction','myaction/main.do','Y',@created_by,getDate());
set @function_id = @@IDENTITY;
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (2,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (3,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (4,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (5,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (6,@function_id,'Y',@created_by,getDate());

-- Report Admin
insert into ats_function (function_name,function_detail,function_module,function_query,home,created_by,created_date) values
 ('Report Admin','Report Admin Search page','Report Admin','auditAdmin/main.do','Y',@created_by,getDate());
set @function_id = @@IDENTITY;
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (2,@function_id,'Y',@created_by,getDate());

insert into ats_function (function_name,function_detail,function_module,function_query,created_by,created_date) values
 ('Report Admin View','Report Admin View page','Report Admin','auditAdmin/view.do',@created_by,getDate());
set @function_id = @@IDENTITY;
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (2,@function_id,'N',@created_by,getDate());

insert into ats_function (function_name,function_detail,function_module,function_query,created_by,created_date) values
 ('Report Admin Edit','Report Admin Edit page','Report Admin','auditAdmin/edit.do',@created_by,getDate());
set @function_id = @@IDENTITY;
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (2,@function_id,'N',@created_by,getDate());

insert into ats_function (function_name,function_detail,function_module,function_query,created_by,created_date) values
 ('Report Admin Delete','Report Admin Delete page','Report Admin','auditAdmin/delete.do',@created_by,getDate());
set @function_id = @@IDENTITY;
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (2,@function_id,'N',@created_by,getDate());

-- Issue Admin
insert into ats_function (function_name,function_detail,function_module,function_query,created_by,created_date) values
 ('Issue Edit','Issue Edit page','Report Admin','issueAdmin/edit.do',@created_by,getDate());
set @function_id = @@IDENTITY;
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (2,@function_id,'N',@created_by,getDate());

insert into ats_function (function_name,function_detail,function_module,function_query,created_by,created_date) values
 ('Issue Delete','Issue Delete page','Report Admin','issueAdmin/delete.do',@created_by,getDate());
set @function_id = @@IDENTITY;
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (2,@function_id,'N',@created_by,getDate());

-- Action Admin
insert into ats_function (function_name,function_detail,function_module,function_query,created_by,created_date) values
 ('Action Edit','Action Edit page','Report Admin','actionAdmin/edit.do',@created_by,getDate());
set @function_id = @@IDENTITY;
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (2,@function_id,'N',@created_by,getDate());

insert into ats_function (function_name,function_detail,function_module,function_query,created_by,created_date) values
 ('Action Delete','Action Delete page','Report Admin','actionAdmin/delete.do',@created_by,getDate());
set @function_id = @@IDENTITY;
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (2,@function_id,'N',@created_by,getDate());

-- Comment Admin
insert into ats_function (function_name,function_detail,function_module,function_query,created_by,created_date) values
 ('Comment Add','Comment Add page','Report Admin','commentAdmin/edit.do',@created_by,getDate());
set @function_id = @@IDENTITY;
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (2,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (3,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (4,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (6,@function_id,'N',@created_by,getDate());

-- Document
insert into ats_function (function_name,function_detail,function_module,function_query,created_by,created_date) values
 ('Document Download','Document Download (report, comment attachment) page','Document','document/download.do',@created_by,getDate());
set @function_id = @@IDENTITY;
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (2,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (3,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (4,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (5,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (6,@function_id,'N',@created_by,getDate());

insert into ats_function (function_name,function_detail,function_module,function_query,created_by,created_date) values
 ('Action Export','Action Export page','Document','document/export.do',@created_by,getDate());
set @function_id = @@IDENTITY;
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (2,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (3,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (4,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (5,@function_id,'N',@created_by,getDate());
insert into ats_role_function (role_id,function_id,home,created_by,created_date) values (6,@function_id,'N',@created_by,getDate());

insert into ats_dbversion (dbversion_id, prev_dbversion_id) values ('ats.1.00', null);
