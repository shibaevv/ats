package net.apollosoft.ats.search;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class SearchCriteria
{

    /** DEFAULT_MAX */
    public static final int DEFAULT_MAX = 100;

    /** maxResults */
    private int maxResults;

    /** pagination */
    private Pagination pagination = new Pagination();

    /**
     * @return the maxResults
     */
    public int getMaxResults()
    {
        return maxResults;
    }

    /**
     * @param maxResults the maxResults to set
     */
    public void setMaxResults(int maxResults)
    {
        this.maxResults = maxResults;
    }

    /**
     * @return the pagination
     */
    public Pagination getPagination()
    {
        return pagination;
    }

    /**
     * @param pagination the pagination to set
     */
    public void setPagination(Pagination pagination)
    {
        this.pagination = pagination;
    }

    /**
     * @return the sort
     */
    public String getSort()
    {
        return pagination.getSort();
    }

    /**
     * @param sort the sort to set
     */
    public void setSort(String sort)
    {
        pagination.setSort(sort);
    }

    /**
     * @return the dir
     */
    public String getDir()
    {
        return pagination.getDir();
    }

    /**
     * @param dir the dir to set
     */
    public void setDir(String dir)
    {
        pagination.setDir(dir);
    }

    /**
     * @return the startIndex
     */
    public int getStartIndex()
    {
        return pagination.getStartIndex();
    }

    /**
     * @param startIndex the startIndex to set
     */
    public void setStartIndex(int startIndex)
    {
        pagination.setStartIndex(startIndex);
    }

    /**
     * @return the pageSize
     */
    public int getPageSize()
    {
        return pagination.getPageSize();
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize)
    {
        pagination.setPageSize(pageSize);
    }
    
    /**
     * @return the totalRecords
     */
    public int getTotalRecords()
    {
        return pagination.getTotalRecords();
    }

    /**
     * @param totalRecords the totalRecords to set
     */
    public void setTotalRecords(int totalRecords)
    {
        pagination.setTotalRecords(totalRecords);
    }

}