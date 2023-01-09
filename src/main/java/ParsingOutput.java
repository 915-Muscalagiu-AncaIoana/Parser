import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ParsingOutput {

    List<Node> getParsingTree(List<String> productionsString, Grammar grammar){
        List<HelperNode> helperNodeList = new ArrayList<>();
        List<HelperNode> nodeStack = new ArrayList<>();
        Integer productionIndex = 0;
        Integer nodeNumber = 0;
        HelperNode node = new HelperNode(grammar.starterNonterminal);
        node.rightSibling = -1;
        nodeNumber+=1;
        node.index = nodeNumber;

        helperNodeList.add(node);
        nodeStack.add(node);

        Integer maxLevel = -1;
        HashMap<Integer,List<HelperNode>> nodesPerLevel = new HashMap<>();

        while(productionIndex < productionsString.size()) {
            HelperNode currentNode = nodeStack.get(0);

            if (grammar.terminals.contains(currentNode.value.strip())) {
                nodeStack.remove(0);
                continue;
            }

            Production production = grammar.productionsSet.getProductionFromIndex(Integer.valueOf(productionsString.get(productionIndex)));
            List<HelperNode> nodesToAdd = new ArrayList<>();

            for (String symbol : production.getRhs()){
                HelperNode child = new HelperNode(symbol);
                child.parent = currentNode;
                child.level = currentNode.level+1;

                if(child.level > maxLevel){
                    maxLevel = child.level;
                }
                if(nodesPerLevel.containsKey(child.level)){
                    nodesPerLevel.get(child.level).add(child);
                }
                else {
                    nodesPerLevel.put(child.level, new ArrayList<>());
                    nodesPerLevel.get(child.level).add(child);
                }
                helperNodeList.add(child);
                nodesToAdd.add(0,child);
            }
            Integer index = nodeStack.indexOf(currentNode);
            nodeStack.remove(currentNode);
            for(Integer i = index ; i < index + nodesToAdd.size();i++){
                nodeStack.add(i,nodesToAdd.get(i));
            }
            productionIndex+=1;

        }
        Integer currentLevel = 0;
        Integer currentIndex = 1;

        while(currentLevel<=maxLevel){
            for(int i = 0;i< helperNodeList.size();i++){
                if(helperNodeList.get(i).level == currentLevel){
                    helperNodeList.get(i).index = currentIndex;
                    currentIndex+=1;
                }
            }
            currentLevel+=1;
        }
        for(int level : nodesPerLevel.keySet()){
            for(int i = 0; i < nodesPerLevel.get(level).size();i++){
                HelperNode helperNode = nodesPerLevel.get(level).get(i);
               if (i == nodesPerLevel.get(level).size() - 1){
                   helperNode.rightSibling = -1;
               }
               else {
                   HelperNode nextHelperNodeOnLevel = nodesPerLevel.get(level).get(i+1);
                   if(helperNode.parent.equals(nextHelperNodeOnLevel.parent) || Objects.equals(helperNode.parent.rightSibling, nextHelperNodeOnLevel.parent.index)){
                       helperNode.rightSibling = nextHelperNodeOnLevel.index;
                   }
                   else {
                       helperNode.rightSibling = -1;
                   }
               }
            }
        }
        List<Node> nodeList = new ArrayList<>();
        for(HelperNode helperNode : helperNodeList){
            Node node1 = new Node(helperNode.value);
            node1.rightSibling = helperNode.rightSibling;
            node1.index = helperNode.index;
            if(helperNode.parent == null){
                node1.parent = -1;
            }
            else {
                node1.parent = helperNode.parent.index;
            }
            nodeList.add(node1);
        }
        return nodeList;
    }

    void printParsingTreeToScreen(List<String> productionsString, Grammar grammar, String fileName){
        List<Node> nodes = getParsingTree(productionsString,grammar);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            for(Node node : nodes){
                System.out.println(node);
                writer.write(String.valueOf(node));
                writer.write('\n');
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
