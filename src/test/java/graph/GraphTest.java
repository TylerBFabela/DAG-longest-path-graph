package graph;

import static org.junit.Assert.*;
import org.junit.Test;

// various tests to ensure that the program works with edge cases
public class GraphTest
{
    @Test
    public void Test1()
    {
        Graph graph = new Graph();
        graph.insert(8); //adds vertex 0 with time 8
        assertEquals(0, graph.get(0).start());
        assertEquals(8, graph.finish());
        Graph.Vertex j1 = graph.insert(3); //adds vertex 1 with time 3
        graph.insert(5); //adds vertex 2 with time 5
        assertEquals(8, graph.finish()); //should return 8, since vertex 0 takes time 8 to complete.
        /* Note it is not the earliest completion time of any vertex, but the earliest the entire set can complete. */
        graph.get(0).requires(graph.get(2)); //vertex 2 must precede vertex 0
        assertEquals(13, graph.finish()); //should return 13 (vertex 0 cannot start until time 5)
        graph.get(0).requires(j1); //vertex 1 must precede vertex 0
        assertEquals(13, graph.finish()); //should return 13
        assertEquals(5, graph.get(0).start()); //should return 5
        assertEquals(0, j1.start()); //should return 0
        assertEquals(0, graph.get(2).start()); //should return 0
        j1.requires(graph.get(2)); //vertex 2 must precede vertex 1
        assertEquals(16, graph.finish()); //should return 16
        assertEquals(8, graph.get(0).start()); //should return 8
        assertEquals(5, graph.get(1).start()); //should return 5
        assertEquals(0, graph.get(2).start()); //should return 0
        graph.get(1).requires(graph.get(0)); //vertex 0 must precede vertex 1 (creates loop)
        assertEquals(-1, graph.finish()); //should return -1
        assertEquals(-1, graph.get(0).start()); //should return -1
        assertEquals(-1, graph.get(1).start()); //should return -1
        assertEquals(0, graph.get(2).start()); //should return 0 (no loops in prerequisites)
        Graph.Vertex j3 = graph.insert(3);
        graph.get(2).requires(j3);
        assertEquals(-1, graph.finish());
        assertEquals(3, graph.get(2).start());
        assertEquals(0, graph.get(3).start());
        graph.get(3).requires(graph.get(2)); // loops
        assertEquals(-1, graph.finish());
        Graph.Vertex j4 = graph.insert(100);
        graph.get(4).requires(j1);
        assertEquals(-1, graph.get(4).start()); // j4 should be -1 but isn't because it isn't updated
        assertEquals(-1, graph.finish());
        Graph.Vertex j5 = graph.insert(1);
        Graph.Vertex j6 = graph.insert(1);
        graph.get(6).requires(j5);
        graph.get(1).requires(j6);
        assertEquals(1, graph.get(6).start());
        assertEquals(0, graph.get(5).start());
        assertEquals(-1, graph.finish());
        graph.get(5).requires(j4);
        assertEquals(-1, graph.get(4).start());
        assertEquals(-1, graph.get(6).start());
        assertEquals(-1, graph.get(5).start());
        Graph.Vertex j7 = graph.insert(1);
        graph.get(1).requires(j7);
        assertEquals(0, graph.get(7).start());
        assertEquals(-1, graph.finish());
    }

    @Test
    public void Test2()
    {
        Graph graph = new Graph();
        assertEquals(0, graph.finish());
        Graph.Vertex j1 = graph.insert(3);
        Graph.Vertex j2 = graph.insert(4);
        Graph.Vertex j3 = graph.insert(5);
        j1.requires(j2);
        j1.requires(j3);
        j3.requires(j2);
        assertEquals(9, j1.start()); //should return 9
        assertEquals(12, graph.finish());
    }

    @Test
    public void Test3()
    {
        Graph graph = new Graph();
        graph.insert(8); //adds vertex 0 with time 8
        Graph.Vertex j1 = graph.insert(3); //adds vertex 1 with time 3
        graph.insert(5); //adds vertex 2 with time 5
        /* Note it is not the earliest completion time of any vertex, but the earliest the entire set can complete. */
        graph.get(0).requires(graph.get(2)); //vertex 2 must precede vertex 0
        graph.get(0).requires(j1); //vertex 1 must precede vertex 0
        j1.requires(graph.get(2)); //vertex 2 must precede vertex 1
        graph.get(1).requires(graph.get(0)); //vertex 0 must precede vertex 1 (creates loop)
        Graph.Vertex j3 = graph.insert(3);
        graph.get(2).requires(j3);
        graph.get(3).requires(graph.get(2)); // loops
        Graph.Vertex j4 = graph.insert(100);
        graph.get(4).requires(j1);
        Graph.Vertex j5 = graph.insert(1);
        Graph.Vertex j6 = graph.insert(1);
        graph.get(6).requires(j5);
        graph.get(1).requires(j6);
        graph.get(5).requires(j4);
        Graph.Vertex j7 = graph.insert(1);
        graph.get(1).requires(j7);

        assertEquals(-1, graph.get(0).start());
        assertEquals(-1, graph.get(1).start());
        assertEquals(-1, graph.get(2).start());
        assertEquals(-1, graph.get(3).start());
        assertEquals(-1, graph.get(4).start());
        assertEquals(-1, graph.get(5).start());
        assertEquals(-1, graph.get(6).start());
        assertEquals(0, graph.get(7).start());
        assertEquals(-1, graph.finish());
    }

    /*
    @Test
    public void TestSelfLoop()
    {
        Graph graph = new Graph();
        assertEquals(0, graph.finish());
        Graph.Vertex j0 = graph.insert(3);
        graph.get(0).requires(graph.get(0));
        assertEquals(-1, graph.get(0).start());
        assertEquals(-1, graph.finish());
    }

     */

    @Test
    public void noLoops()
    {
        Graph graph = new Graph();
        Graph.Vertex j0 = graph.insert(0); //adds vertex 0 with time 0
        assertEquals(0, graph.finish());
        Graph.Vertex j1 = graph.insert(1); //adds vertex 0 with time 1
        Graph.Vertex j2 = graph.insert(2); //adds vertex 0 with time 2
        Graph.Vertex j3 = graph.insert(3); //adds vertex 0 with time 3
        Graph.Vertex j4 = graph.insert(4); //adds vertex 0 with time 4
        Graph.Vertex j5 = graph.insert(5); //adds vertex 0 with time 5
        Graph.Vertex j6 = graph.insert(6); //adds vertex 0 with time 6
        Graph.Vertex j7 = graph.insert(7); //adds vertex 0 with time 7
        Graph.Vertex j8 = graph.insert(8); //adds vertex 0 with time 8
        Graph.Vertex j9 = graph.insert(9); //adds vertex 0 with time 9

        graph.get(0).requires(j1);
        graph.get(2).requires(j3);
        graph.get(1).requires(j2);

        graph.get(4).requires(j5);
        graph.get(5).requires(j6);

        graph.get(8).requires(j9);
        graph.get(7).requires(j8);

        assertEquals(6, graph.get(0).start());
        assertEquals(5, graph.get(1).start());
        assertEquals(3, graph.get(2).start());
        assertEquals(0, graph.get(3).start());
        assertEquals(11, graph.get(4).start());
        assertEquals(6, graph.get(5).start());
        assertEquals(0, graph.get(6).start());
        assertEquals(17, graph.get(7).start());
        assertEquals(9, graph.get(8).start());
        assertEquals(0, graph.get(9).start());
    }

    @Test
    public void noLoopsIntertwined()
    {
        Graph graph = new Graph();
        Graph.Vertex j0 = graph.insert(0); //adds vertex 0 with time 0
        Graph.Vertex j1 = graph.insert(1); //adds vertex 0 with time 1
        Graph.Vertex j2 = graph.insert(2); //adds vertex 0 with time 2
        Graph.Vertex j3 = graph.insert(3); //adds vertex 0 with time 3
        Graph.Vertex j4 = graph.insert(4); //adds vertex 0 with time 4
        Graph.Vertex j5 = graph.insert(5); //adds vertex 0 with time 5
        Graph.Vertex j6 = graph.insert(6); //adds vertex 0 with time 6
        Graph.Vertex j7 = graph.insert(7); //adds vertex 0 with time 7
        Graph.Vertex j8 = graph.insert(8); //adds vertex 0 with time 8
        Graph.Vertex j9 = graph.insert(9); //adds vertex 0 with time 9

        graph.get(0).requires(j1);
        graph.get(2).requires(j3);
        graph.get(1).requires(j2);

        graph.get(4).requires(j5);
        graph.get(5).requires(j6);

        graph.get(8).requires(j9);
        graph.get(7).requires(j8);



        assertEquals(6, graph.get(0).start());
        assertEquals(5, graph.get(1).start());
        assertEquals(3, graph.get(2).start());
        assertEquals(0, graph.get(3).start());
        assertEquals(11, graph.get(4).start());
        assertEquals(6, graph.get(5).start());
        assertEquals(0, graph.get(6).start());
        assertEquals(17, graph.get(7).start());
        assertEquals(9, graph.get(8).start());
        assertEquals(0, graph.get(9).start());

        graph.get(1).requires(j4);

        graph.get(7).requires(j0);

        assertEquals(16, graph.get(0).start());
        assertEquals(15, graph.get(1).start());
        assertEquals(3, graph.get(2).start());
        assertEquals(0, graph.get(3).start());
        assertEquals(11, graph.get(4).start());
        assertEquals(6, graph.get(5).start());
        assertEquals(0, graph.get(6).start());
        assertEquals(17, graph.get(7).start());
        assertEquals(9, graph.get(8).start());
        assertEquals(0, graph.get(9).start());
    }

    @Test
    public void tester()
    {
        Graph graph = new Graph();
        Graph.Vertex j0 = graph.insert(0); //adds vertex 0 with time 0
        Graph.Vertex j1 = graph.insert(0); //adds vertex 0 with time 1
        Graph.Vertex j2 = graph.insert(10); //adds vertex 0 with time 2

        graph.get(0).requires(j1);
        graph.get(1).requires(j2);
        graph.get(0).requires(j2);


        assertEquals(10, graph.get(0).start());
        assertEquals(10, graph.get(1).start());
        assertEquals(0, graph.get(2).start());
    }

    @Test
    public void tester2()
    {
        Graph graph = new Graph();
        Graph.Vertex j2 = graph.insert(10); //adds vertex 0 with time 2
        Graph.Vertex j1 = graph.insert(0); //adds vertex 0 with time 1
        Graph.Vertex j0 = graph.insert(0); //adds vertex 0 with time 0

        j0.requires(j1);
        j1.requires(j2);
        j0.requires(j2);


        assertEquals(10, j0.start());
        assertEquals(10, j1.start());
        assertEquals(0, j2.start());
    }

    @Test
    public void Testers1()
    {
        Graph graph = new Graph();
        graph.insert(8); //adds vertex 0 with time 8
        assertEquals(0, graph.get(0).start());
        assertEquals(8, graph.finish());
        Graph.Vertex j1 = graph.insert(3); //adds vertex 1 with time 3
        graph.insert(5); //adds vertex 2 with time 5
        assertEquals(8, graph.finish()); //should return 8, since vertex 0 takes time 8 to complete.
        /* Note it is not the earliest completion time of any vertex, but the earliest the entire set can complete. */
        graph.get(0).requires(graph.get(2)); //vertex 2 must precede vertex 0
        assertEquals(13, graph.finish()); //should return 13 (vertex 0 cannot start until time 5)
        graph.get(0).requires(j1); //vertex 1 must precede vertex 0
        assertEquals(13, graph.finish()); //should return 13
        assertEquals(5, graph.get(0).start()); //should return 5
        assertEquals(0, j1.start()); //should return 0
        assertEquals(0, graph.get(2).start()); //should return 0
        j1.requires(graph.get(2)); //vertex 2 must precede vertex 1
        assertEquals(16, graph.finish()); //should return 16
        assertEquals(8, graph.get(0).start()); //should return 8
        assertEquals(5, graph.get(1).start()); //should return 5
        assertEquals(0, graph.get(2).start()); //should return 0
        graph.get(1).requires(graph.get(0)); //vertex 0 must precede vertex 1 (creates loop)
        assertEquals(-1, graph.finish()); //should return -1
        assertEquals(-1, graph.get(0).start()); //should return -1
        assertEquals(-1, graph.get(1).start()); //should return -1
        assertEquals(0, graph.get(2).start()); //should return 0 (no loops in prerequisites)
        Graph.Vertex j3 = graph.insert(3);
        graph.get(2).requires(j3);
        assertEquals(-1, graph.finish());
        assertEquals(3, graph.get(2).start());
        assertEquals(0, graph.get(3).start());
        graph.get(3).requires(graph.get(2)); // loops
        assertEquals(-1, graph.finish());
        Graph.Vertex j4 = graph.insert(100);
        graph.get(4).requires(j1);
        assertEquals(-1, graph.get(4).start()); // j4 should be -1 but isn't because it isn't updated
        assertEquals(-1, graph.finish());
        Graph.Vertex j5 = graph.insert(1);
        Graph.Vertex j6 = graph.insert(1);
        graph.get(6).requires(j5);
        graph.get(1).requires(j6);
        assertEquals(1, graph.get(6).start());
        assertEquals(0, graph.get(5).start());
        assertEquals(-1, graph.finish());
        graph.get(5).requires(j4);
        assertEquals(-1, graph.get(4).start());
        assertEquals(-1, graph.get(6).start());
        assertEquals(-1, graph.get(5).start());
        Graph.Vertex j7 = graph.insert(1);
        graph.get(1).requires(j7);
        assertEquals(0, graph.get(7).start());
        assertEquals(-1, graph.finish());
        Graph.Vertex j8 = graph.insert(3);
        Graph.Vertex j9 = graph.insert(3);
        graph.get(8).requires(j9);
        graph.get(9).requires(j1);
        assertEquals(-1, graph.get(8).start());
        assertEquals(-1, graph.get(9).start());
        assertEquals(-1, graph.finish());
    }

    @Test
    public void testWithManyBaseVertexsAndVertexsWithoutParents()
    {
        Graph graph = new Graph();
        Graph.Vertex j0 = graph.insert(0);
        Graph.Vertex j1 = graph.insert(1);
        Graph.Vertex j2 = graph.insert(2);
        Graph.Vertex j3 = graph.insert(3);
        Graph.Vertex j4 = graph.insert(4);
        Graph.Vertex j5 = graph.insert(5);
        Graph.Vertex j6 = graph.insert(6);
        Graph.Vertex j7 = graph.insert(7);
        Graph.Vertex j8 = graph.insert(8);
        j0.requires(j4);
        j1.requires(j4);
        j2.requires(j4);
        j3.requires(j4);
        j4.requires(j5);
        j4.requires(j6);
        j4.requires(j7);
        j4.requires(j8);
        assertEquals(12, graph.get(0).start());
        assertEquals(12, graph.get(1).start());
        assertEquals(12, graph.get(2).start());
        assertEquals(12, graph.get(3).start());
        assertEquals(8, graph.get(4).start());
        assertEquals(0, graph.get(5).start());
        assertEquals(0, graph.get(6).start());
        assertEquals(0, graph.get(7).start());
        assertEquals(0, graph.get(8).start());
        assertEquals(15, graph.finish());
    }
}

