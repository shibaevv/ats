package net.apollosoft.ats.audit.domain;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public interface ITemplate extends net.apollosoft.ats.domain.ITemplate
{

    /** L001 - Application Help Link, see L001.txt */
    String L001 = "L001";

    /** L002 - Search Reports Help Link, see L002.txt */
    String L002 = "L002";

    /** L003 - View Report Issue/Action Help Link, see L003.txt */
    String L003 = "L003";

    /** L004 - Search Actions Help Link, see L004.txt */
    String L004 = "L004";

    /** R001 - Email notification to person responsible when report/action published, see R001.vm */
    String R001 = "R001";

    /** R002 - Email notification to oversight team when report/action published, see R002.vm */
    String R002 = "R002";

    /** R003 - Email to users with Oversight Team role day 1 and day 15 each month, see R003.vm */
    String R003 = "R003";

    /** R004 - Email to person responsible on day 1 each month, see R004.vm */
    String R004 = "R004";
    
    /** A001 - Action published, see A001.vm */
    String A001 = "A001";
    
    /** A002 - Action title change, see A002.vm */
    String A002 = "A002";
    
    /** A003 - Action responsible person added, see A003.vm */
    String A003 = "A003";
    
    /** A004 - Action responsible person removed, see A004.vm */
    String A004 = "A004";
    
    /** A005 - Action group / division added, see A005.vm */
    String A005 = "A005";
    
    /** A006 - Action group / division removed, see A006.vm */
    String A006 = "A006";
    
    /** A007 - Action due date change, see A007.vm */
    String A007 = "A007";
    
    /** A008 - Action business status change, see A008.vm */
    String A008 = "A008";
    
    /** A009 - Action follow up status change, see A009.vm */
    String A009 = "A009";
    
    /** A010 - Action description change, see A010.vm */
    String A010 = "A010";

}