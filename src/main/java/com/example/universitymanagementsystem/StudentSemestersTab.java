package com.example.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.io.IOException;
import java.sql.Connection;

public class StudentSemestersTab {

    @FXML
    private Button semesterTab;

    public void setButtonText(String semester) {
        semesterTab.setText(semester);
    }


    public static Node createButton(String semester,
                                  StudentRegisterController parentController) {
        try {
            FXMLLoader loader = new FXMLLoader(StudentSemestersTab.class.getResource("/com/example/universitymanagementsystem/studentsemesterstab.fxml"));
            Node cardNode = loader.load();

            StudentSemestersTab controller = loader.getController();
            controller.setButtonText(semester);
            return cardNode;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
