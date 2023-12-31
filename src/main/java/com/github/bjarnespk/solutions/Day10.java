package com.github.bjarnespk.solutions;

import com.github.bjarnespk.main.App;
import com.github.bjarnespk.main.DayTemplate;
import com.github.bjarnespk.main.Part;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Day10 implements DayTemplate {

    private static final int SIZE = 140;

    @Override
    public String solve(Part part, InputStream in) throws IOException {
        bytes = readLinesAsArray(in);

        if (part == Part.PART_ONE) {
            return String.valueOf(solveOne());
        }
        return String.valueOf(solveTwo());
    }

    private int solveTwo() {
        List<Node> visited = getNodeList();
        deleteNonLoopNodes(new HashSet<>(visited));

        int growthFactor = 2;
        int newSize = enlargeGrid(growthFactor);
        addPseudoEdges(visited, growthFactor);

        bfs(newSize);

        return countInnerNodes(growthFactor);
    }

    private int countInnerNodes(int growthFactor) {
        int innerNodes = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (bytes[i * growthFactor + 1][j * growthFactor + 1] == 0) {
                    innerNodes++;
                }
            }
        }
        return innerNodes;
    }

    private void bfs(int newSize) {
        Queue<Node> queue = new LinkedList<>();
        queue.offer(new Node(0, 0, '*'));
        bytes[0][0] = '*';
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            queue.addAll(getBFSAdjacent(node, newSize));
        }
    }

    private Collection<Node> getBFSAdjacent(Node node, int newSize) {
        Set<Node> set = new HashSet<>();
        if (node.y() > 0 && bytes[node.y() - 1][node.x()] == 0) {
            set.add(new Node(node.y() - 1, node.x(), '*'));
            bytes[node.y() - 1][node.x()] = '*';
        }
        if (node.x() > 0 && bytes[node.y()][node.x() - 1] == 0) {
            set.add(new Node(node.y(), node.x() - 1, '*'));
            bytes[node.y()][node.x() - 1] = '*';
        }
        if (node.y() < newSize - 1 && bytes[node.y() + 1][node.x()] == 0) {
            set.add(new Node(node.y() + 1, node.x(), '*'));
            bytes[node.y() + 1][node.x()] = '*';
        }
        if (node.x() < newSize - 1 && bytes[node.y()][node.x() + 1] == 0) {
            set.add(new Node(node.y(), node.x() + 1, '*'));
            bytes[node.y()][node.x() + 1] = '*';
        }
        return set;
    }

    private void addPseudoEdges(List<Node> visited, int growthFactor) {
        visited.add(visited.get(0));
        var it = visited.iterator();
        Node first = it.next();
        while (it.hasNext()) {
            Node second = it.next();
            int newY = ((first.y() * growthFactor + 1) + (second.y() * growthFactor + 1)) / 2;
            int newX = ((first.x() * growthFactor + 1) + (second.x() * growthFactor + 1)) / 2;
            bytes[newY][newX] = '*';
            first = second;
        }
        visited.remove(visited.size() - 1);
    }

    private int enlargeGrid(int growthFactor) {
        int newSize = SIZE * growthFactor + 2;
        byte[][] newGrid = new byte[newSize][newSize];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (bytes[i][j] != '.') {
                    newGrid[i * growthFactor + 1][j * growthFactor + 1] = bytes[i][j];
                }
            }
        }
        bytes = newGrid;
        return newSize;
    }

    private void deleteNonLoopNodes(Set<Node> visited) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Node currentNode = new Node(i, j, (char) bytes[i][j]);
                if (!visited.contains(currentNode)) {
                    bytes[i][j] = 0;
                }
            }
        }
    }

    private List<Node> getNodeList() {
        List<Node> visited = new LinkedList<>();
        visited.add(startNode);
        List<Node> nodes = adjacentNodes(startNode);

        while (!nodes.isEmpty()) {
            Node node = nodes.get(0);
            visited.add(node);
            nodes = adjacentNodes(node);
            nodes.removeAll(visited);
        }
        return visited;
    }

    private int solveOne() {
        Set<Node> visited = new HashSet<>();
        visited.add(startNode);
        List<Node> nodes = adjacentNodes(startNode);
        int distance = 1;

        while (!nodes.isEmpty()) {
            Node node = nodes.get(0);
            visited.add(node);
            nodes = adjacentNodes(node);
            nodes.removeAll(visited);
            distance++;
        }

        return (distance + 1) / 2;
    }

    private List<Node> adjacentNodes(Node node) {
        List<Node> list = new LinkedList<>();
        if (node.y() > 0) {
            byte nextNode = bytes[node.y() - 1][node.x()];
            if ((node.symbol() == 'S' || node.symbol() == '|' || node.symbol() == 'L' || node.symbol == 'J')
                    && (nextNode == 'F' || nextNode == '|' || nextNode == '7')) {
                Node newNode = new Node(node.y() - 1, node.x(), (char) bytes[node.y() - 1][node.x()]);
                list.add(newNode);
            }
        }
        if (node.x() > 0) {
            byte nextNode = bytes[node.y()][node.x() - 1];
            if ((node.symbol() == 'S' || node.symbol() == '-' || node.symbol() == '7' || node.symbol == 'J')
                    && (nextNode == '-' || nextNode == 'L' || nextNode == 'F')) {
                Node newNode = new Node(node.y(), node.x() - 1, (char) bytes[node.y()][node.x() - 1]);
                list.add(newNode);
            }
        }
        if (node.y() < SIZE - 1) {
            byte nextNode = bytes[node.y() + 1][node.x()];
            if ((node.symbol() == 'S' || node.symbol() == 'F' || node.symbol() == '|' || node.symbol == '7')
                    && (nextNode == '|' || nextNode == 'L' || nextNode == 'J')) {
                Node newNode = new Node(node.y() + 1, node.x(), (char) bytes[node.y() + 1][node.x()]);
                list.add(newNode);
            }
        }
        if (node.x() < SIZE - 1) {
            byte nextNode = bytes[node.y()][node.x() + 1];
            if ((node.symbol() == 'S' || node.symbol() == '-' || node.symbol() == 'L' || node.symbol == 'F')
                    && (nextNode == '-' || nextNode == '7' || nextNode == 'J')) {
                Node newNode = new Node(node.y(), node.x() + 1, (char) bytes[node.y()][node.x() + 1]);
                list.add(newNode);
            }
        }
        return list;
    }

    private record Node(int y, int x, char symbol) { }

    private static Node startNode;
    private byte[][] bytes;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private byte[][] readLinesAsArray(InputStream in) throws IOException {
        byte[][] bytes = new byte[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            if (in.read(bytes[i]) != SIZE) {
                throw new RuntimeException("Not enough bytes");
            }
            for (int j = 0; j < SIZE; j++) {
                if (bytes[i][j] == 'S') {
                    startNode = new Node(i, j, 'S');
                }
            }
            in.read();
        }
        return bytes;
    }
}
