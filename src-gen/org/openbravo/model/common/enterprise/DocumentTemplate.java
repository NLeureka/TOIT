/*
 *************************************************************************
 * The contents of this file are subject to the Openbravo  Public  License
 * Version  1.1  (the  "License"),  being   the  Mozilla   Public  License
 * Version 1.1  with a permitted attribution clause; you may not  use this
 * file except in compliance with the License. You  may  obtain  a copy of
 * the License at http://www.openbravo.com/legal/license.html
 * Software distributed under the License  is  distributed  on  an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific  language  governing  rights  and  limitations
 * under the License.
 * The Original Code is Openbravo ERP.
 * The Initial Developer of the Original Code is Openbravo SLU
 * All portions are Copyright (C) 2008-2011 Openbravo SLU
 * All Rights Reserved.
 * Contributor(s):  ______________________________________.
 ************************************************************************
*/
package org.openbravo.model.common.enterprise;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openbravo.base.structure.ActiveEnabled;
import org.openbravo.base.structure.BaseOBObject;
import org.openbravo.base.structure.ClientEnabled;
import org.openbravo.base.structure.OrganizationEnabled;
import org.openbravo.base.structure.Traceable;
import org.openbravo.model.ad.access.User;
import org.openbravo.model.ad.system.Client;
/**
 * Entity class for entity DocumentTemplate (stored in table C_POC_DOCTYPE_TEMPLATE).
 *
 * NOTE: This class should not be instantiated directly. To instantiate this
 * class the {@link org.openbravo.base.provider.OBProvider} should be used.
 */
public class DocumentTemplate extends BaseOBObject implements Traceable, ClientEnabled, OrganizationEnabled, ActiveEnabled {
    private static final long serialVersionUID = 1L;
    public static final String TABLE_NAME = "C_POC_DOCTYPE_TEMPLATE";
    public static final String ENTITY_NAME = "DocumentTemplate";
    public static final String PROPERTY_ID = "id";
    public static final String PROPERTY_CLIENT = "client";
    public static final String PROPERTY_ORGANIZATION = "organization";
    public static final String PROPERTY_ACTIVE = "active";
    public static final String PROPERTY_CREATIONDATE = "creationDate";
    public static final String PROPERTY_CREATEDBY = "createdBy";
    public static final String PROPERTY_UPDATED = "updated";
    public static final String PROPERTY_UPDATEDBY = "updatedBy";
    public static final String PROPERTY_DOCUMENTTYPE = "documentType";
    public static final String PROPERTY_NAME = "name";
    public static final String PROPERTY_TEMPLATELOCATION = "templateLocation";
    public static final String PROPERTY_REPORTFILENAME = "reportFilename";
    public static final String PROPERTY_TEMPLATEFILENAME = "templateFilename";
    public static final String PROPERTY_SHOWCOMPANYDATA = "showcompanydata";
    public static final String PROPERTY_SHOWLOGO = "showlogo";
    public static final String PROPERTY_HEADERMARGIN = "headermargin";
    public static final String PROPERTY_EMAILTEMPLATELIST = "emailTemplateList";

    public DocumentTemplate() {
        setDefaultValue(PROPERTY_ACTIVE, true);
        setDefaultValue(PROPERTY_SHOWCOMPANYDATA, true);
        setDefaultValue(PROPERTY_SHOWLOGO, true);
        setDefaultValue(PROPERTY_EMAILTEMPLATELIST, new ArrayList<Object>());
    }

    @Override
    public String getEntityName() {
        return ENTITY_NAME;
    }

    public String getId() {
        return (String) get(PROPERTY_ID);
    }

    public void setId(String id) {
        set(PROPERTY_ID, id);
    }

    public Client getClient() {
        return (Client) get(PROPERTY_CLIENT);
    }

    public void setClient(Client client) {
        set(PROPERTY_CLIENT, client);
    }

    public Organization getOrganization() {
        return (Organization) get(PROPERTY_ORGANIZATION);
    }

    public void setOrganization(Organization organization) {
        set(PROPERTY_ORGANIZATION, organization);
    }

    public Boolean isActive() {
        return (Boolean) get(PROPERTY_ACTIVE);
    }

    public void setActive(Boolean active) {
        set(PROPERTY_ACTIVE, active);
    }

    public Date getCreationDate() {
        return (Date) get(PROPERTY_CREATIONDATE);
    }

    public void setCreationDate(Date creationDate) {
        set(PROPERTY_CREATIONDATE, creationDate);
    }

    public User getCreatedBy() {
        return (User) get(PROPERTY_CREATEDBY);
    }

    public void setCreatedBy(User createdBy) {
        set(PROPERTY_CREATEDBY, createdBy);
    }

    public Date getUpdated() {
        return (Date) get(PROPERTY_UPDATED);
    }

    public void setUpdated(Date updated) {
        set(PROPERTY_UPDATED, updated);
    }

    public User getUpdatedBy() {
        return (User) get(PROPERTY_UPDATEDBY);
    }

    public void setUpdatedBy(User updatedBy) {
        set(PROPERTY_UPDATEDBY, updatedBy);
    }

    public DocumentType getDocumentType() {
        return (DocumentType) get(PROPERTY_DOCUMENTTYPE);
    }

    public void setDocumentType(DocumentType documentType) {
        set(PROPERTY_DOCUMENTTYPE, documentType);
    }

    public String getName() {
        return (String) get(PROPERTY_NAME);
    }

    public void setName(String name) {
        set(PROPERTY_NAME, name);
    }

    public String getTemplateLocation() {
        return (String) get(PROPERTY_TEMPLATELOCATION);
    }

    public void setTemplateLocation(String templateLocation) {
        set(PROPERTY_TEMPLATELOCATION, templateLocation);
    }

    public String getReportFilename() {
        return (String) get(PROPERTY_REPORTFILENAME);
    }

    public void setReportFilename(String reportFilename) {
        set(PROPERTY_REPORTFILENAME, reportFilename);
    }

    public String getTemplateFilename() {
        return (String) get(PROPERTY_TEMPLATEFILENAME);
    }

    public void setTemplateFilename(String templateFilename) {
        set(PROPERTY_TEMPLATEFILENAME, templateFilename);
    }

    public Boolean isShowcompanydata() {
        return (Boolean) get(PROPERTY_SHOWCOMPANYDATA);
    }

    public void setShowcompanydata(Boolean showcompanydata) {
        set(PROPERTY_SHOWCOMPANYDATA, showcompanydata);
    }

    public Boolean isShowlogo() {
        return (Boolean) get(PROPERTY_SHOWLOGO);
    }

    public void setShowlogo(Boolean showlogo) {
        set(PROPERTY_SHOWLOGO, showlogo);
    }

    public String getHeadermargin() {
        return (String) get(PROPERTY_HEADERMARGIN);
    }

    public void setHeadermargin(String headermargin) {
        set(PROPERTY_HEADERMARGIN, headermargin);
    }

    @SuppressWarnings("unchecked")
    public List<EmailTemplate> getEmailTemplateList() {
        return (List<EmailTemplate>) get(PROPERTY_EMAILTEMPLATELIST);
    }

    public void setEmailTemplateList(List<EmailTemplate> emailTemplateList) {
        set(PROPERTY_EMAILTEMPLATELIST, emailTemplateList);
    }

}
