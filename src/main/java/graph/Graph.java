package graph;

import java.util.ArrayList;
import java.util.List;

public class Graph<V extends Graph.Vertex>
{
    protected List<V> vertices;
    protected int longestPath;
    protected boolean needsUpdate;

    public Graph()
    {
        this.vertices = new ArrayList<>();
        this.longestPath = 0;
        this.needsUpdate = false;
    }

    protected V createVertex(int weight) { return (V) new Vertex(this, weight); }

    public V insert(int weight)
    {
        if (this.longestPath != -1 && weight > this.longestPath) // true if there is a longer weight to wait
        {
            this.longestPath = weight;
        }

        V v = createVertex(weight);
        this.vertices.add(v);
        return v;
    }

    public V get(int index) { return vertices.get(index); }

    public int finish()
    {
        if (this.longestPath == -1) // true if there is a cycle
        {
            return this.longestPath;
        }

        if (this.needsUpdate && !vertices.isEmpty()) // true if the vertices to check need to be updated
        {
            this.needsUpdate = false;
            this.vertices.get(0).updateGraph();
        }

        return this.longestPath;
    }

    public List<V> BFSTopology()
    {
        if (this.vertices.isEmpty())
        {
            return new ArrayList<>();
        }
        return vertices.get(0).BFS(new ArrayList<>());
    }

    public List<V> DFSTopology()
    {
        if (this.vertices.isEmpty())
        {
            return new ArrayList<>();
        }
        vertices.get(0).undiscoverAll();
        return vertices.get(0).DFS(new ArrayList<>());
    }

    public static class Vertex
    {
        protected int weight;
        protected int netWeight;
        protected int netInDegrees;
        protected int inDegree;
        protected ArrayList<Vertex> parents;
        protected Graph<?> graph;
        protected boolean discovered;

        protected Vertex(Graph<?> graph, int weight)
        {
            this.weight = weight;
            this.netWeight = 0;
            this.inDegree = 0;
            this.netInDegrees = 0;
            this.parents = new ArrayList<>();
            this.graph = graph;
            this.discovered = false;
        }

        public void requires(Vertex v)
        {
            graph.needsUpdate = true;
            v.parents.add(this);
            this.netInDegrees++;
        }

        public int start()
        {
            if (this.netWeight == -1) // true if this is already in a cycle
            {
                return -1;
            }

            if (graph.needsUpdate) // true if the vertices to check need to be updated
            {
                graph.needsUpdate = false;
                this.updateGraph();
            }

            return this.netWeight;
        }

        protected void updateGraph()
        {
            this.updateInDegree();
            List<Vertex> acyclicList = this.BFS(new ArrayList<>());

            for (int i = 0; i < acyclicList.size(); i++) // loops through the list of acyclic nodes
            {
                Vertex u = acyclicList.get(i);

                for (int v = 0; v < u.parents.size(); v++) // loops through the parents of u
                {
                    u.relax(u.parents.get(v));
                }
            }

            boolean hasCycles = false;

            for (int i = 0; i < graph.vertices.size(); i++) // loops through the inDegrees list
            {
                Vertex v = graph.vertices.get(i);
                if (v.inDegree > 0) // true if a node is not acyclic
                {
                    hasCycles = true;
                    v.netWeight = -1;
                }
            }

            if (hasCycles) // true if at least one node is in a newly found cycle
            {
                graph.longestPath = -1;
            }
        }

        protected void updateInDegree()
        {
            for (int i = 0; i < graph.vertices.size(); i++) // loops through all the vertices
            {
                Vertex v = graph.vertices.get(i);
                v.inDegree = v.netInDegrees;
            }
        }

        protected <T extends Vertex> List<T> BFS(List<T> vertList)
        {
            for (int i = 0; i < graph.vertices.size(); i++) // loops through all the vertices
            {
                Vertex v = graph.vertices.get(i);
                if (v.inDegree == 0) // true if there are no in degrees
                {
                    vertList.add((T) v);
                }
            }

            for (int i = 0; i < vertList.size(); i++) // loops through the vertList
            {
                Vertex u = vertList.get(i);

                for (int v = 0; v < u.parents.size(); v++) // loops through the parents of each node
                {
                    Vertex parent = u.parents.get(v);
                    parent.inDegree--;

                    if (parent.inDegree == 0) // true if the vertex has an in degree of 0
                    {
                        vertList.add((T) parent);
                    }
                }
            }

            return vertList;
        }

        protected <T extends Vertex> List<T> DFS(List<T> vertList)
        {
            this.discovered = true;

            for (int i = 0; i < this.parents.size(); i++) // loops through the list of vertices
            {
                Vertex parent = this.parents.get(i);
                if (!parent.discovered) // true if the vertex should be added to the list
                {
                    parent.DFS(vertList);
                }
            }

            vertList.add((T) this);
            return vertList;
        }

        protected void undiscoverAll()
        {
            for (int i = 0; i < graph.vertices.size(); i++)
            {
                graph.vertices.get(i).discovered = false;
            }
        }

        protected void relax(Vertex v)
        {
            if (v.netWeight == -1) // true if there is not a cycle in v
            {
                return;
            }

            if (this.netWeight + this.weight > v.netWeight) // true if a longer weight is found
            {
                v.netWeight = this.netWeight + this.weight;
            }

            // true if there isn't already a cycle in the graph and a new longest weight is found
            if (graph.longestPath != -1 && v.netWeight + v.weight > graph.longestPath)
            {
                graph.longestPath = v.netWeight + v.weight;
            }
        }
    }
}
