package net.apollosoft.ats.search;

import java.io.Serializable;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class Pagination implements Serializable
{

    /** serialVersionUID */
    private static final long serialVersionUID = -4530749412040466422L;

    /** pageSize */
    private int pageSize;

    /** startIndex */
    private int startIndex;

    /** totalRecords */
    private int totalRecords;

    /** 
     * sort
     * format: 'alias_property1__alias_property2' 
     */
    private String sort;

    /** dir */
    private String dir;

    /**
     * Default ctor - no pagination, just sorting
     */
    public Pagination()
    {
        this.startIndex = 0;
        this.pageSize = 0; //no pagination
        this.dir = IDir.DESC;
    }

    /**
     * 
     * @param pageSize
     * @param startIndex
     * @param sort
     * @param dir
     */
    public Pagination(int pageSize, int startIndex, String sort, String dir)
    {
        this.pageSize = pageSize;
        this.startIndex = startIndex;
        this.sort = sort;
        this.dir = dir;
    }

    /**
     * @return the pageSize
     */
    public int getPageSize()
    {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    /**
     * @return the totalRecords
     */
    public int getTotalRecords()
    {
        return totalRecords;
    }

    /**
     * @param totalRecords the totalRecords to set
     */
    public void setTotalRecords(int totalRecords)
    {
        this.totalRecords = totalRecords;
    }

    /**
     * @return the startIndex
     */
    public int getStartIndex()
    {
        return startIndex;
    }

    /**
     * @param startIndex the startIndex to set
     */
    public void setStartIndex(int startIndex)
    {
        this.startIndex = startIndex;
    }

    /**
     * @return the dir
     */
    public String getDir()
    {
        return dir;
    }

    /**
     * @param dir the dir to set
     */
    public void setDir(String dir)
    {
        this.dir = dir;
    }

    /**
     * @return the sort
     */
    public String getSort()
    {
        return sort;
    }

    /**
     * @param sort the sort to set
     */
    public void setSort(String sort)
    {
        this.sort = sort;
    }

}