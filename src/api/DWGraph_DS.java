package ex2.src.api;

import java.util.Collection;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph {
    @Override
    public node_data getNode(int key) {
        return null;
    }

    @Override
    public edge_data getEdge(int src, int dest) {
        return null;
    }

    @Override
    public void addNode(node_data n) {

    }

    @Override
    public void connect(int src, int dest, double w) {

    }

    @Override
    public Collection<node_data> getV() {
        return null;
    }

    @Override
    public Collection<edge_data> getE(int node_id) {
        return null;
    }

    @Override
    public node_data removeNode(int key) {
        return null;
    }

    @Override
    public edge_data removeEdge(int src, int dest) {
        return null;
    }

    @Override
    public int nodeSize() {
        return 0;
    }

    @Override
    public int edgeSize() {
        return 0;
    }

    @Override
    public int getMC() {
        return 0;
    }

    private class Node_Data implements node_data {
        private int idNode;
        private double weight;
        private geo_location geo;
        private HashMap<Integer, node_data> neighbor;
        private String info;
        private int tag;


        @Override
        public int getKey() {
            return this.idNode;
        }

        public Node_Data(int key, double w) {

            this.idNode = key;
            this.weight=w;
            this.neighbor = new HashMap<>();
            this.geo= new Geo_Location();
            this.info = "";
            this.tag = 0;
        }

        public Node_Data(node_data n) {
            this.idNode = n.getKey();
            this.neighbor = new HashMap<>();
            this.info = n.getInfo() + "";
            this.tag = n.getTag();
            this.weight=n.getWeight();
            this.geo= new Geo_Location(n.getLocation());
        }

        public Collection<node_data> getNi() {
            return neighbor.values();
        }

        public void removeNei(node_data node) {
            this.neighbor.remove(node.getKey());
        }

        @Override
        public geo_location getLocation() {
            return this.geo;
        }

        //////////////////////
        @Override
        public void setLocation(geo_location p) {

        }

        @Override
        public double getWeight() {
            return this.weight;
        }

        @Override
        public void setWeight(double w) {
            this.weight=w;
        }

        @Override
        public String getInfo() {
            return info;
        }

        @Override
        public void setInfo(String s) {
            this.info= s+"";
        }

        @Override
        public int getTag() {
            return this.tag;
        }

        @Override
        public void setTag(int t) {
            this.tag=t;
        }

        public String toString() {
            return "" + idNode;
        }

        /**
         * This method returns if this object and this node_info equals of their values.
         *
         * @param o
         * @return
         * */
        @Override
        public boolean equals(Object o){
            if(o==null) return false;
            if(!(o instanceof node_data)) return false;
            int k= ((node_data)o).getKey();
            if(getKey()!=k) return false;
           /* if(getV(this.getKey())!=null && getV(k)!=null){
                if(!(getV(this.getKey()).equals(getV(k)))) return false;
            }
            if(getTag()!=((node_info) o).getTag()) return false;
            if(!(getInfo().equals(((node_info) o).getInfo()))) return false;
            return true;
        }*/
            return true;
        }

        private class Geo_Location implements geo_location {
            private double x;
            private double y;
            private double z;

            public Geo_Location(){
                this.x=0;
                this.y=0;
                this.z=0;
            }

            public Geo_Location(double x, double y, double z){
                this.x=x;
                this.y=y;
                this.z=z;
            }

            public Geo_Location(geo_location g){
                this.x=g.x();
                this.y=g.y();
                this.z=g.z();
            }

            @Override
            public double x() {
                return x;
            }

            @Override
            public double y() {
                return y;
            }

            @Override
            public double z() {
                return z;
            }

            //////////////////////////////
            /////fix/////////
            @Override
            public double distance(geo_location g) {
                return 0;
            }
        }
    }
    private class Edge_DS implements edge_data  {
    	private int src;
    	private int dest;
    	private String info;
    	private int tag;
    	private double weight;

    		@Override
    		public int getSrc() {
    			// TODO Auto-generated method stub
    			return this.src;
    		}

    		@Override
    		public int getDest() {
    			// TODO Auto-generated method stub
    			return this.dest;
    		}

    		@Override
    		public double getWeight() {
    			// TODO Auto-generated method stub
    			return this.weight;
    		}

    		@Override
    		public String getInfo() {
    			// TODO Auto-generated method stub
    			return this.info;
    		}

    		@Override
    		public void setInfo(String s) {
    			this.info = s;
    			
    		}

    		@Override
    		public int getTag() {
    			// TODO Auto-generated method stub
    			return this.tag;
    		}

    		@Override
    		public void setTag(int t) {
    			this.tag=t;
    			
    		}

    	}
}