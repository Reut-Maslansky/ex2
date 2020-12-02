package api;

import Server.Game_Server_Ex2;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gameClient.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class Ex2 implements Runnable{
    private static MyFrame _win;
    private static Arena _ar;
    public static void main(String[] args) {
        Thread client = new Thread(new Ex2());
        client.start();
        System.out.println("start");
    }
    @Override
    public void run() {
      //  Scanner s= new Scanner(System.in);
       // int scenario_num = s.nextInt();
        int scenario_num=1;
        game_service game = Game_Server_Ex2.getServer(scenario_num);
        //int id= s.nextInt();
        //game.login(id);

        directed_weighted_graph gg = loadGraph(game.getGraph());
        init(game);

        game.startGame();

        _win.setTitle("Ex2 - OOP: (NONE trivial Solution) "+game.toString());
        int ind=0;
        long dt=100;
//
//        SimplePlayer player = new SimplePlayer("data/music.mp3");
//        Thread playerThread = new Thread(player);
//        playerThread.start();
        while(game.isRunning()) {
            moveAgents(game, gg);
            try {
                if(ind%1==0) {_win.repaint();}
                Thread.sleep(dt);
                ind++;
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        String res = game.toString();

        System.out.println(res);
        System.exit(0);
    }
    private directed_weighted_graph loadGraph(String s){
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(DWGraph_DS.class, new DWGraphJsonDeserializer());
        Gson gson = builder.create();
        try
        {
            directed_weighted_graph g = gson.fromJson(s,DWGraph_DS.class);
            return g;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private void init(game_service game) {
        String pks = game.getPokemons();
        directed_weighted_graph gg = loadGraph(game.getGraph());
        _ar = new Arena();
        _ar.setGraph(gg);
        _ar.setPokemons(Arena.json2Pokemons(pks));
        _win = new MyFrame("Catch Them All");
        _win.setSize(1000, 700);
        _win.update(_ar);

        _win.show();
        String info = game.toString();
        JSONObject line;
        try {
            line = new JSONObject(info);
            JSONObject gameData = line.getJSONObject("GameServer");
            int rs = gameData.getInt("agents");
            System.out.println(info);
            System.out.println(game.getPokemons());
            int src_node = 0;  // arbitrary node, you should start at one of the pokemon
            ArrayList<CL_Pokemon> pksArr = Arena.json2Pokemons(game.getPokemons());
            for (int i = 0; i < pksArr.size(); i++) {
                Arena.updateEdge(pksArr.get(i), gg);
            }
            //decides where we should place the agent
            //on an edge that is accessible to the pokemon
            for (int i = 0; i < rs; i++) {
                CL_Pokemon c = pksArr.get(i%pksArr.size());
                int nn = c.get_edge().getDest();
                    if (c.getType() < 0) {
                        nn = c.get_edge().getSrc();
                    }
                game.addAgent(nn);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * Moves each of the agents along the edge,
     * in case the agent is on a node the next destination (next edge) is chosen (randomly).
     * @param game
     * @param gg
     * @param
     */
    private static void moveAgents(game_service game, directed_weighted_graph gg) {
        String lg = game.move();
        List<CL_Agent> ageA = Arena.getAgents(lg, gg);
        _ar.setAgents(ageA);
        //ArrayList<OOP_Point3D> rs = new ArrayList<OOP_Point3D>();
        String pk =  game.getPokemons();
        _ar.setPokemons(Arena.json2Pokemons(pk));

        for(int i=0;i<ageA.size();i++) {
            CL_Agent ag = ageA.get(i);
            int id = ag.getID();
            int dest = ag.getNextNode();
            int src = ag.getSrcNode();
            double v = ag.getValue();
            if(dest==-1) {
                dest = nextNode(gg, src);
                game.chooseNextEdge(ag.getID(), dest);
                System.out.println("Agent: "+id+", val: "+v+"   turned to node: "+dest);
            }
        }
    }
    /**
     * a very simple random walk implementation!
     * @param g
     * @param src
     * @return
     */

    ///main algorithm
    private static int nextNode(directed_weighted_graph g, int src) {
        int ans = -1;
        Collection<edge_data> ee = g.getE(src);
        Iterator<edge_data> itr = ee.iterator();
        int s = ee.size();
        int r = (int)(Math.random()*s);
        int i=0;
        while(i<r) {itr.next();i++;}
        ans = itr.next().getDest();
        return ans;
    }
}