/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.application;

import java.util.Date;

public class Notification implements Comparable {
    public static enum NOTIFICATION_TYPE {
        APPROVAL,
        FYI,
        CAPTURED_IMAGE,
        CAPTURED_NOTE
    }//enum
    private long _id;
    private String _subject;
    private String _description;
    private NOTIFICATION_TYPE _type = NOTIFICATION_TYPE.FYI;
    private Date _creationDate = new Date();
    private String _image;

    public Notification() {
        super();
    }//constructor

    public Notification(long id, String subject, String description, NOTIFICATION_TYPE type, Date creationDate) {
        super();
        _id = id;
        _subject = subject;
        _description = description;
        _type = type;
        _creationDate = creationDate;
    }//constructor

    @Override
    public int compareTo(Object o) {
        if (o instanceof Notification)
            return _creationDate.compareTo(((Notification) o).getCreationDate()) * -1;
        return 0;
    }//compareTo

    //Accessors
    public void setId(long l) { _id = l; }
    public long getId() { return _id; }
    public void setSubject(String s) { _subject = s; }
    public String getSubject() { return _subject; }
    public void setDescription(String s) { _description = s; }
    public String getDescription() { return _description; }
    public void setType(String t) {
        switch (t) {
            case "CAPTURED_IMAGE":
                _type = NOTIFICATION_TYPE.CAPTURED_IMAGE;
                break;
            case "CAPTURED_NOTE":
                _type = NOTIFICATION_TYPE.CAPTURED_NOTE;
                break;
            case "APPROVAL":
                _type = NOTIFICATION_TYPE.APPROVAL;
                break;
            case "FYI":
                _type = NOTIFICATION_TYPE.FYI;
                break;
            default:
                _type = NOTIFICATION_TYPE.FYI;
        }//switch-case
    }//setType
    public String getType() { return _type.toString(); }
    public void setCreationDate(Date d) { _creationDate = d; }
    public Date getCreationDate() { return _creationDate; }
    public void setImage(String s) { _image = s; }
    public String getImage() { return _image; }
}//Notification
