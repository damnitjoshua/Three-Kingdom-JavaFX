package com.example.fxfinal;

import java.util.ArrayList;
import java.util.List;

public class TeamFormation {
    public List<TreeNode> people;
    
    public TeamFormation(List<TreeNode> people){
        this.people = people;
    }
        
    public List<List<TreeNode>> teamByAttribute(String attributeName) {
        List<List<TreeNode>> groups = new ArrayList<>();

        List<TreeNode> teamS = findByAttribute(attributeName, 250, Integer.MAX_VALUE);
        groups.add(teamS);

        List<TreeNode> teamA = findByAttribute(attributeName, 220, 249);
        groups.add(teamA);

        List<TreeNode> teamB = findByAttribute(attributeName, 190, 219);
        groups.add(teamB);

        List<TreeNode> teamC = findByAttribute(attributeName, Integer.MIN_VALUE, 189);
        groups.add(teamC);

        return groups;
    }

    private List<TreeNode> findByAttribute(String attributeName, int minVal, int maxVal) {
        List<TreeNode> group = new ArrayList<>();
        int n = people.size();

        // Iterate over all combinations of three distinct individuals
        for (int i = 0; i < n - 2; i++) {
            TreeNode person1 = people.get(i);
            if (person1.getGeneral().getIntelligence() > maxVal) {
                break;  // No need to continue searching
            }

            for (int j = i + 1; j < n - 1; j++) {
                TreeNode person2 = people.get(j);
                if (person1 == person2 || person2.getGeneral().getIntelligence() > maxVal) {
                    continue;  // Skip if person2 is the same as person1 or has intelligence exceeding the maximum
                }

                for (int k = j + 1; k < n; k++) {
                    TreeNode person3 = people.get(k);
                    if (person1 == person3 || person2 == person3 || person3.getGeneral().getIntelligence() > maxVal) {
                        continue;  // Skip if person3 is the same as person1 or person2 or has intelligence exceeding the maximum
                    }

                    int totalAttr = 0;
                    switch (attributeName) {
                        case "Strength":
                            totalAttr = person1.getGeneral().getStrength() + person2.getGeneral().getStrength() + person3.getGeneral().getStrength();
                            break;
                        case "Intelligence":
                            totalAttr = person1.getGeneral().getIntelligence() + person2.getGeneral().getIntelligence() + person3.getGeneral().getIntelligence();
                            break;
                        case "Leadership":
                            totalAttr = person1.getGeneral().getLeadership() + person2.getGeneral().getLeadership() + person3.getGeneral().getLeadership();
                            break;
                        case "Politic":
                            totalAttr = person1.getGeneral().getPolitic() + person2.getGeneral().getPolitic() + person3.getGeneral().getPolitic();
                            break;
                    }

                    // Check if the total attribute falls within the specified range
                    if (totalAttr >= minVal && totalAttr <= maxVal) {
                        group.add(person1);
                        group.add(person2);
                        group.add(person3);

                        people.remove(person1);
                        people.remove(person2);
                        people.remove(person3);

                        return group;
                    }
                }
            }
        }
        return group;
    }
  
}
