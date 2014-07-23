use ATS;

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- DROP FUNCTION f_ats_audit_group_division
CREATE FUNCTION f_ats_audit_group_division(@audit_id numeric(19,0))
RETURNS varchar(512)
-- WITH SCHEMABINDING 
AS
BEGIN
    DECLARE @name varchar(100),
			@result varchar(512),
			@cursor CURSOR

	SET @cursor = CURSOR
	FOR 
        SELECT g.group_name + ISNULL(' ' + d.division_name, ' All')
        FROM dbo.ats_audit AS a INNER JOIN
             dbo.ats_audit_group_division AS link ON a.audit_id = link.audit_id INNER JOIN
             dbo.ats_group AS g ON link.group_id = g.group_id LEFT OUTER JOIN
             dbo.ats_division AS d ON link.division_id = d.division_id
        WHERE (a.audit_id = @audit_id)

	SELECT @result = ''

	OPEN @cursor 

	FETCH NEXT FROM @cursor INTO @name
	WHILE @@FETCH_STATUS = 0 
	BEGIN
        IF (@result != '')
        BEGIN
            SELECT @result = @result + ','
        END
		SELECT @result = @result + @name
		FETCH NEXT FROM @cursor INTO @name
	END

	CLOSE @cursor 
	DEALLOCATE @cursor 

	RETURN @result
END
GO
-- select dbo.f_ats_audit_group_division(1);
-- select dbo.f_ats_audit_group_division(1);

-- DROP FUNCTION f_ats_audit_borm
CREATE FUNCTION f_ats_audit_borm(@audit_id numeric(19,0))
RETURNS varchar(512)
-- WITH SCHEMABINDING 
AS
BEGIN
    DECLARE @name varchar(100),
			@result varchar(512),
			@cursor CURSOR

	SET @cursor = CURSOR
	FOR 
		SELECT e.first_name + ' ' + e.last_name + ' - ' + e.userid
        FROM dbo.ats_user AS e INNER JOIN
             dbo.ats_user_group_division AS link2 ON e.id = link2.user_id INNER JOIN
             dbo.ats_audit_group_division AS link ON link2.group_id = link.group_id
        WHERE (link.audit_id = @audit_id) AND (link2.user_type_id = 1)
            AND ((link.division_id IS NOT NULL AND link2.division_id = link.division_id) OR
                 (link.division_id IS NULL AND link2.division_id IS NULL))

	SELECT @result = ''

	OPEN @cursor 

	FETCH NEXT FROM @cursor INTO @name
	WHILE @@FETCH_STATUS = 0 
	BEGIN 
        IF (@result != '')
        BEGIN
            SELECT @result = @result + ','
        END
		SELECT @result = @result + @name
		FETCH NEXT FROM @cursor INTO @name
	END

	CLOSE @cursor 
	DEALLOCATE @cursor 

	RETURN @result
END
GO
-- select dbo.f_ats_audit_borm(1);
-- select dbo.f_ats_audit_borm(1);

-- DROP FUNCTION f_ats_action_group_division
CREATE FUNCTION f_ats_action_group_division(@action_id numeric(19,0))
RETURNS varchar(512)
-- WITH SCHEMABINDING 
AS
BEGIN
    DECLARE @name varchar(100),
			@result varchar(512),
			@cursor CURSOR

	SET @cursor = CURSOR
	FOR 
        SELECT g.group_name + ISNULL(' ' + d.division_name, ' All')
        FROM dbo.ats_action AS a INNER JOIN
             dbo.ats_action_group_division AS link ON a.action_id = link.action_id INNER JOIN
             dbo.ats_group AS g ON link.group_id = g.group_id LEFT OUTER JOIN
             dbo.ats_division AS d ON link.division_id = d.division_id
        WHERE (a.action_id = @action_id)

	SELECT @result = ''

	OPEN @cursor 

	FETCH NEXT FROM @cursor INTO @name
	WHILE @@FETCH_STATUS = 0 
	BEGIN 
        IF (@result != '')
        BEGIN
            SELECT @result = @result + ','
        END
		SELECT @result = @result + @name
		FETCH NEXT FROM @cursor INTO @name
	END

	CLOSE @cursor 
	DEALLOCATE @cursor 

	RETURN @result
END
GO
-- select dbo.f_ats_action_group_division(1);

-- DROP FUNCTION f_ats_action_borm
CREATE FUNCTION f_ats_action_borm(@action_id numeric(19,0))
RETURNS varchar(512)
-- WITH SCHEMABINDING 
AS
BEGIN
    DECLARE @name varchar(100),
			@result varchar(512),
			@cursor CURSOR

	SET @cursor = CURSOR
	FOR 
		SELECT e.first_name + ' ' + e.last_name + ' - ' + e.userid
        FROM dbo.ats_user AS e INNER JOIN
             dbo.ats_user_group_division AS link2 ON e.id = link2.user_id INNER JOIN
             dbo.ats_action_group_division AS link ON link2.group_id = link.group_id
        WHERE (link.action_id = @action_id) AND (link2.user_type_id = 1)
            AND ((link.division_id IS NOT NULL AND link2.division_id = link.division_id) OR
                 (link.division_id IS NULL AND link2.division_id IS NULL))

	SELECT @result = ''

	OPEN @cursor 

	FETCH NEXT FROM @cursor INTO @name
	WHILE @@FETCH_STATUS = 0 
	BEGIN 
        IF (@result != '')
        BEGIN
            SELECT @result = @result + ','
        END
		SELECT @result = @result + @name
		FETCH NEXT FROM @cursor INTO @name
	END

	CLOSE @cursor 
	DEALLOCATE @cursor 

	RETURN @result
END
GO
-- select dbo.f_ats_action_borm(1);

-- DROP FUNCTION f_ats_action_responsible
CREATE FUNCTION f_ats_action_responsible(@action_id numeric(19,0))
RETURNS varchar(512)
-- WITH SCHEMABINDING 
AS
BEGIN
    DECLARE @name varchar(100),
			@result varchar(512),
			@cursor CURSOR

	SET @cursor = CURSOR
	FOR 
		SELECT e.first_name + ' ' + e.last_name + ' - ' + e.userid
        FROM dbo.ats_action AS a INNER JOIN
             dbo.ats_action_responsible AS link ON a.action_id = link.action_id INNER JOIN
             dbo.ats_user AS e ON link.user_id = e.id
		WHERE a.action_id = @action_id

	SELECT @result = ''

	OPEN @cursor 

	FETCH NEXT FROM @cursor INTO @name
	WHILE @@FETCH_STATUS = 0 
	BEGIN 
        IF (@result != '')
        BEGIN
            SELECT @result = @result + ','
        END
		SELECT @result = @result + @name
		FETCH NEXT FROM @cursor INTO @name
	END

	CLOSE @cursor 
	DEALLOCATE @cursor 

	RETURN @result
END
GO
-- select dbo.f_ats_action_responsible(1);

-- DROP FUNCTION f_ats_action_last_comment
CREATE FUNCTION [dbo].[f_ats_action_last_comment](@action_id numeric(19,0))
RETURNS varchar(8000)
--WITH SCHEMABINDING 
AS
BEGIN
    DECLARE @result varchar(8000)

	SELECT TOP 1 @result = c.comment_detail
	FROM dbo.ats_action a, dbo.ats_comment c
	WHERE a.action_id = @action_id
	  and c.action_id = a.action_id
      and c.audit_log = 'N'
    ORDER BY c.created_date desc

	RETURN @result
END
GO
-- select dbo.f_ats_action_last_comment(1);

-- DROP FUNCTION f_ats_action_last_comment_user
CREATE FUNCTION [dbo].[f_ats_action_last_comment_date](@action_id numeric(19,0))
RETURNS datetime
--WITH SCHEMABINDING 
AS
BEGIN
    DECLARE @result datetime

	SELECT TOP 1 @result = c.created_date
	FROM dbo.ats_action a, dbo.ats_comment c
	WHERE a.action_id = @action_id
	  and c.action_id = a.action_id
      and c.audit_log = 'N'
    ORDER BY c.created_date desc

	RETURN @result
END
GO
-- select dbo.f_ats_action_last_comment_user(1);

-- DROP FUNCTION f_ats_action_last_comment_date
CREATE FUNCTION [dbo].[f_ats_action_last_comment_user](@action_id numeric(19,0))
RETURNS varchar(512)
--WITH SCHEMABINDING 
AS
BEGIN
    DECLARE @result varchar(512)

	SELECT TOP 1 @result = (e.first_name + ' ' + e.last_name + ' - ' + e.userid)
	FROM dbo.ats_action a, dbo.ats_comment c, dbo.ats_user e
	WHERE a.action_id = @action_id
	  and c.action_id = a.action_id
	  and c.created_by = e.id
      and c.audit_log = 'N'
    ORDER BY c.created_date desc

	RETURN @result
END
GO
-- select dbo.f_ats_action_last_comment_date(1);

-- DROP FUNCTION f_ats_audit_actionOpen
CREATE FUNCTION f_ats_audit_actionOpen(@audit_id numeric(19,0))
RETURNS numeric(19,0)
-- WITH SCHEMABINDING 
AS
BEGIN
    DECLARE @result numeric(19,0)

    SELECT @result = count(a.action_id)
    FROM dbo.ats_action a INNER JOIN
        dbo.ats_business_status bs ON a.business_status_id = bs.business_status_id INNER JOIN
        dbo.ats_issue i ON a.issue_id = i.issue_id
    WHERE i.audit_id = @audit_id
        AND bs.action_status_id = 1
        AND a.deleted_date IS NULL
        AND a.published_by IS NOT NULL AND a.published_date IS NOT NULL

	RETURN @result
END
GO
-- select dbo.f_ats_audit_actionOpen(1);

-- DROP FUNCTION f_ats_audit_actionTotal
CREATE FUNCTION f_ats_audit_actionTotal(@audit_id numeric(19,0))
RETURNS numeric(19,0)
-- WITH SCHEMABINDING 
AS
BEGIN
    DECLARE @result numeric(19,0)

    SELECT @result = count(a.action_id)
    FROM dbo.ats_action a INNER JOIN
        dbo.ats_issue i ON a.issue_id = i.issue_id
    WHERE i.audit_id = @audit_id
        AND a.deleted_date IS NULL
        AND a.published_by IS NOT NULL AND a.published_date IS NOT NULL

	RETURN @result
END
GO
-- select dbo.f_ats_audit_actionTotal(1);

-- DROP FUNCTION f_ats_audit_issueUnpublished
CREATE FUNCTION f_ats_audit_issueUnpublished(@audit_id numeric(19,0))
RETURNS numeric(19,0)
-- WITH SCHEMABINDING 
AS
BEGIN
    DECLARE @result numeric(19,0)

    SELECT @result = count(i.issue_id)
    FROM dbo.ats_issue i
    WHERE i.audit_id = @audit_id
        AND i.deleted_date IS NULL
        AND i.published_by IS NULL AND i.published_date IS NULL

	RETURN @result
END
GO
-- select dbo.f_ats_audit_issueUnpublished(1);

-- DROP FUNCTION f_ats_audit_actionUnpublished
CREATE FUNCTION f_ats_audit_actionUnpublished(@audit_id numeric(19,0))
RETURNS numeric(19,0)
-- WITH SCHEMABINDING 
AS
BEGIN
    DECLARE @result numeric(19,0)

    SELECT @result = count(a.action_id)
    FROM dbo.ats_action a INNER JOIN
        dbo.ats_issue i ON a.issue_id = i.issue_id
    WHERE i.audit_id = @audit_id
        AND a.deleted_date IS NULL
        AND a.published_by IS NULL AND a.published_date IS NULL

	RETURN @result
END
GO
-- select dbo.f_ats_audit_actionUnpublished(1);
