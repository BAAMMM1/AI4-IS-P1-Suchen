package mvc.model.algorithm;

import mvc.model.field.Node;
import mvc.model.field.Field;
import mvc.model.field.NodeSnapShot;
import mvc.model.field.NodeType;

import java.util.*;

public abstract class SearchAlgorithm {

    protected Field field;

    protected Node source;
    protected Node target;

    protected List<Node> openList;
    protected List<Node> closeList;
    protected List<Node> path;
    protected List<NodeSnapShot> snapShots;

    private long startTime;
    private long endTime;

    public SearchAlgorithm(Field field, int source, int target) {
        this.field = field;
        this.source = field.getField()[source];
        this.target = field.getField()[target];
        this.openList = new ArrayList<Node>();
        this.closeList = new ArrayList<Node>();
        this.path = new ArrayList<Node>();
        this.snapShots = new ArrayList<>();
    }

    public void calculate() {
        this.openList = new ArrayList<Node>();
        this.closeList = new ArrayList<Node>();
        this.path = new ArrayList<Node>();
        this.snapShots = new ArrayList<>();

        snapShots.add(new NodeSnapShot(source, NodeType.SOURCE));
        snapShots.add(new NodeSnapShot(target, NodeType.TARGET));

        startTime();
        execute();
        endTime();

    }

    public long getEndTime() {
        return endTime;
    }

    protected abstract void execute();

    protected void tracePath(Node node) {
        System.out.println("tracee path");

//        node.setDepth(node.getParent().getDepth() + 1);

        List<Node> result = new ArrayList<>();
        Node currenNode = node;

        result.add(node);

        while (currenNode.getParent() != null) {
            //System.out.println("current: " + currenNode + " parent: " + currenNode.getParent());
            //System.out.println(currenNode);
            result.add(currenNode.getParent());
            currenNode = currenNode.getParent();
            snapShots.add(new NodeSnapShot(currenNode, NodeType.PATH));
        }

        Collections.reverse(result);

        path = result;

    }



    protected void snapShotsAdd(Node node){
        snapShots.add(new NodeSnapShot(node, node.getType()));
    }


    protected void closeListAdd(Node node) {
        snapShots.add(new NodeSnapShot(node, NodeType.CLOSELIST));
        closeList.add(node);
    }


    protected void pathAdd(Node node) {
        path.add(node);
        snapShots.add(new NodeSnapShot(node, NodeType.PATH));
    }

    public Field getField() {
        return field;
    }

    public Node getSource() {
        return source;
    }

    public Node getTarget() {
        return target;
    }

    public List<Node> getOpenList() {
        return openList;
    }

    public List<Node> getCloseList() {
        return closeList;
    }

    public List<Node> getPath() {
        return path;
    }

    public int getStorageComplexity() {
        return openList.size() + closeList.size();
    }

    protected void startTime() {
        startTime = System.nanoTime();
    }

    protected void endTime() {
        endTime = System.nanoTime();
    }

    public long getTime() {
        return endTime - startTime;
    }

    public List<NodeSnapShot> getSnapShots() {
        return snapShots;
    }

    public void setCloseList(List<Node> closeList) {
        this.closeList = closeList;
    }
}
