package net.apollosoft.ats.audit.service;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import net.apollosoft.ats.domain.IDocument;
import net.apollosoft.ats.domain.ITemplate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public interface DocumentService
{

    /**
     * used for login details in generated documents
     * @param baseUrl
     */
    void setBaseUrl(String baseUrl);

    /**
     * 
     * @param auditId
     * @return
     * @throws ServiceException
     */
    IDocument findById(Long documentId) throws ServiceException;

    /**
     * 
     * @param tableData
     * @param output
     * @throws ServiceException
     */
    void exportCsv(List<Object[]> tableData, OutputStream output) throws ServiceException;

    /**
     * 
     * @param tableData
     * @param output
     * @throws ServiceException
     */
    void exportExcel(List<Object[]> tableData, OutputStream output) throws ServiceException;

    /**
     * 
     * @param audit optional
     * @param template
     * @param output
     * @throws ServiceException
     */
    void createDocument(ITemplate template, Map<String, Object> params, OutputStream output) throws ServiceException;

}