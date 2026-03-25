package org.GUI;

public class ResourceTester {
    public static void main(String[] args) {
        printResource("/GUI/Request Loan.fxml");
        printResource("/GUI/Artefact Details.fxml");
        printResource("/GUI/Start Page.fxml");
        printResource("/GUI/NonExistent.fxml");
    }

    private static void printResource(String path) {
        var res = ResourceTester.class.getResource(path);
        System.out.println("Resource '" + path + "' resolved to: " + res);
    }
}

