module com.example.sempro {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.eclipse.milo.opcua.stack.core;
    requires org.eclipse.milo.opcua.stack.client;
    requires org.eclipse.milo.opcua.sdk.client;
    requires java.logging;
    requires java.sql;
    requires org.postgresql.jdbc;
    //requires org.apache.commons.lang3;
    requires java.desktop;


    opens com.example.sempro to javafx.fxml;
    exports com.example.sempro;
    exports domain;
    exports  database;
}