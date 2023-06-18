package com.example.fxfinal;

import java.util.LinkedList;
import java.util.List;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose an encryption method:");
        System.out.println("1. AES Encryption");
        System.out.println("2. Caesar Cipher");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (choice == 1) {
            System.out.print("Enter the secret key: ");
            String secretKey = scanner.nextLine();

            AESEncryption aesEncryption = new AESEncryption(secretKey);

            System.out.print("Enter the message to encrypt: ");
            String message = scanner.nextLine();

            String encryptedMessage = aesEncryption.encrypt(message);
            System.out.println("Encrypted message: " + encryptedMessage);

            String decryptedMessage = aesEncryption.decrypt(encryptedMessage);
            System.out.println("Decrypted message: " + decryptedMessage);
        } else if (choice == 2) {
            System.out.print("Enter the shift value: ");
            int shift = scanner.nextInt();
            scanner.nextLine();

            CaesarCipher caesarCipher = new CaesarCipher(shift);

            System.out.print("Enter the message to encrypt: ");
            String message = scanner.nextLine();

            String encryptedMessage = caesarCipher.encrypt(message);
            System.out.println("Encrypted message: " + encryptedMessage);

            String decryptedMessage = caesarCipher.decrypt(encryptedMessage);
            System.out.println("Decrypted message: " + decryptedMessage);
        } else {
            System.out.println("Invalid choice. Exiting...");
        }

        // persona
        Persona SunQuan = new Persona("Sun Quan", "Cavalry", 96, 98, 72, 77, 95);
        Persona ZhangZhao = new Persona("Zhang Zhao", "Archer", 22, 80, 89, 99, 60);
        Persona ZhouYu = new Persona("Zhou Yu", "Cavalry", 80, 86, 97, 80, 90);
        Persona[] generals = new Persona[]{
                new Persona("Xu Sheng", "Archer", 90, 78, 72, 40, 94),
                new Persona("Zhu Ge Jin", "Archer", 63, 61, 88, 82, 71),
                new Persona("Lu Su", "Infantry", 43, 87, 84, 88, 53),
                new Persona("Tai Shi Chi", "Cavalry", 96, 81, 43, 33, 97),
                new Persona("Xiao Qiao", "Infantry", 42, 52, 89, 77, 34),
                new Persona("Da Qiao", "Cavalry", 39, 62, 90, 62, 41),
                new Persona("Zhou Tai", "Infantry", 92, 89, 72, 43, 99),
                new Persona("Gan Ning", "Archer", 98, 92, 45, 23, 97),
                new Persona("Lu Meng", "Cavalry", 70, 77, 93, 83, 88),
                new Persona("Huang Gai", "Infantry", 83, 98, 72, 42, 89)
        };

        TreeNode emperor = new TreeNode(SunQuan);
        TreeNode chiefMilitary = new TreeNode(ZhouYu);
        TreeNode chiefManagement = new TreeNode(ZhangZhao);

        emperor.addChild(chiefMilitary);
        emperor.addChild(chiefManagement);

        LinkedList<TreeNode> newList = new LinkedList<>();
        //newList is for sorting purpose. if we use general only, discard code below
        newList.add(chiefManagement);
        newList.add(chiefMilitary);

        //assigning general to their department to create 3 level hierarchy
        assignGenerals(generals, chiefManagement, chiefMilitary, newList);

        //show in hierarcy. but i dont know how to make the output show in tree form
        emperor.printList();

        //sorting the list by attribute
        LinkedList<TreeNode> strength = sortingMethod.sortStrength(new LinkedList<>(newList));
        LinkedList<TreeNode> leadership = sortingMethod.sortLeadership(new LinkedList<>(newList));
        LinkedList<TreeNode> intelligence = sortingMethod.sortIntelligence(new LinkedList<>(newList));
        LinkedList<TreeNode> politic = sortingMethod.sortPolitic(new LinkedList<>(newList));
        LinkedList<TreeNode> hitpoint = sortingMethod.sortHitPoint(new LinkedList<>(newList));

        //show the general after sorting by atributes
        System.out.println("Sort By Strength: ");
        generalOut (strength);
        System.out.println("Sort By Leaderhip: ");
        generalOut (leadership);
        System.out.println("Sort By Intelligence: ");
        generalOut (intelligence);
        System.out.println("Sort By Politic: ");
        generalOut (politic);
        System.out.println("Sort By HitPoint: ");
        generalOut (hitpoint);

        Scanner key = new Scanner(System.in);

        System.out.print("Enter the attribute name (Strength, Intelligence, Leadership, Politic, HitPoint): ");
        String attributeName = key.nextLine();
        System.out.print("Enter the attribute value: ");
        int attributeValue = key.nextInt();

        LinkedList<TreeNode> searchResult = new LinkedList<>();

        switch(attributeName.toLowerCase()){
            case "strength":
                searchResult = sortingMethod.searchGeneralByAttribute(strength, attributeName, attributeValue);
                break;
            case "intelligence":
                searchResult = sortingMethod.searchGeneralByAttribute(intelligence, attributeName, attributeValue);
                break;
            case "leadership":
                searchResult = sortingMethod.searchGeneralByAttribute(leadership, attributeName, attributeValue);
                break;
            case "politic":
                searchResult = sortingMethod.searchGeneralByAttribute(politic, attributeName, attributeValue);
                break;
            case "hitpoint":
                searchResult = sortingMethod.searchGeneralByAttribute(hitpoint, attributeName, attributeValue);
                break;
        }

        if (searchResult.isEmpty()) {
            System.out.println("\nNo general with the specified attribute value found.");
        } else {
            System.out.println("\nGenerals with the specified attribute value:");
            for (TreeNode node : searchResult) {
                System.out.println(node.getGeneral().getName());
            }
        }
        System.out.println();

        //Team formation
        TeamFormation StrCreator = new TeamFormation(strength);
        TeamFormation IntelCreator = new TeamFormation(intelligence);
        TeamFormation LeadCreator = new TeamFormation(leadership);
        TeamFormation PolCreator = new TeamFormation(politic);

        //assign team
        List<List<TreeNode>> StrTeam = StrCreator.teamByAttribute("Strength");
        List<List<TreeNode>> IntelTeam = IntelCreator.teamByAttribute("Intelligence");
        List<List<TreeNode>> LeadTeam = LeadCreator.teamByAttribute("Leadership");
        List<List<TreeNode>> PolTeam = PolCreator.teamByAttribute("Politic");

        printTeam(StrTeam, "Strength");
        printTeam(IntelTeam, "Intelligence");
        printTeam(LeadTeam, "Leadership");
        printTeam(PolTeam, "Politic");


        int[][] matrix = {
                {1, 1, 0, 0},
                {1, 0, 0, 0},
                {1, 0, 1, 1},
                {1, 0, 0, 0}
        };

        BattleshipClusters BC = new BattleshipClusters();

        int numClusters = BC.countBattleshipClusters(matrix);
        System.out.println("Number of clusters: " + numClusters);

        List<int[]> optimalCoordinates = BC.findOptimalCoordinates(matrix);
        System.out.println("Optimal coordinates for fireball throwing:");

        for (int[] coordinate : optimalCoordinates) {
            System.out.println("[" + coordinate[0] + ", " + coordinate[1] + "]");
        }

        PathFinder PF = new PathFinder();

        int[][] maze = {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {2, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1},
                {1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1},
                {1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1},
                {1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1},
                {1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1},
                {1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 3},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };

        boolean[][] currentPath = new boolean[maze.length][maze[0].length];

        // Find the path
        PF.findPath(maze, currentPath);

        // Display the maze with the path
        PF.displayMaze(maze, currentPath);
    }

    //assign the general can be as void but i return as list to kill two bird with one stone
    private static LinkedList<TreeNode> assignGenerals(Persona[] generals, TreeNode chiefManagement, TreeNode chiefMilitary, LinkedList newList) {
        for (Persona general : generals) {
            TreeNode generalNode = new TreeNode(general);
            newList.add(generalNode);
            if (general.getIntelligence() > general.getStrength()) {
                chiefManagement.addChild(generalNode);
            } else {
                chiefMilitary.addChild(generalNode);
            }
        }

        return newList;
    }

    //output for the sorting part
    private static void generalOut (List<TreeNode> general){
        for (TreeNode node : general) {
            System.out.println(node.getGeneral());
        }
        System.out.println("---------------------------------------");
    }

    //output for team
    private static void printTeam(List<List<TreeNode>> teams, String teamType) {
        System.out.println("Groups by Total " + teamType + ":");
        for (int i = 0; i < teams.size(); i++) {
            System.out.println("Group " + (i + 1) + ":");
            List<TreeNode> group = teams.get(i);
            if (group.isEmpty()) {
                System.out.println("No people in this group.");
            } else {
                int totalScore = 0;
                for (TreeNode person : group) {
                    String personName = person.getGeneral().getName();
                    int score = 0;
                    switch (teamType) {
                        case "Strength":
                            score = person.getGeneral().getStrength();
                            break;
                        case "Intelligence":
                            score = person.getGeneral().getIntelligence();
                            break;
                        case "Leadership":
                            score = person.getGeneral().getLeadership();
                            break;
                        case "Politic":
                            score = person.getGeneral().getPolitic();
                            break;
                    }
                    System.out.println("Name: " + personName + ", " + teamType + ": " + score);
                    totalScore += score;
                }
                System.out.println("Total " + teamType + ": " + totalScore);
            }
            System.out.println();
        }
    }
}

