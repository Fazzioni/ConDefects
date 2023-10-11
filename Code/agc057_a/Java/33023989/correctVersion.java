/*
 	created by krps
 	本体は634行目あたりのsolve()に書いてあります。
 	Good Luck!
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;


public class Main implements Runnable {


	static FastScanner sc = new FastScanner();
	static PrintWriter Systemout = new PrintWriter(System.out);
		public static class Pair<K, V> extends AbstractMap.SimpleEntry<K, V> implements Comparable<Pair<K, V>> {
		    public Pair(final K key, final V value) {
		        super(key, value);
		    }
			@Override
			public int compareTo(Pair<K, V> o) {

				Comparable key = (Comparable)this.getKey();
				Comparable key2 = (Comparable)o.getKey();

				/*if (false) {
					Comparable key3 = (Comparable) this.getValue();
					Comparable key4 = (Comparable) o.getValue();
					if (key.compareTo(key2) == 0) {
						return key3.compareTo(key4);
					}
				}*/
				return key.compareTo(key2);
			}
		}
	private static boolean isPrime(long t) {
		if(t<2)return false;
		for(int i=2;i*i<=t;i++) {
			if(t%i==0)return false;
		}
		return true;
	}
	@SuppressWarnings("unused")
	private static long ncr(long n,long r) {
		long res=1;

		for(int i=0;i<r;i++) {
			res*=n-i;
			res/=i+1;
		}
		return res;
	}
	@SuppressWarnings("unused")
	private static int StringCount(String T,String v) {
		int res=0;
		int t=0;
		while(T.indexOf(v,t)>=0) {
			//p(t);
			res++;
			t=T.indexOf(v,t)+1;
		}
		return res;
	}
	private static void swap(long V[],int a,int b) {
		long temp=V[b];
		V[b]=V[a];
		V[a]=temp;
	}
	private static void p(long[][] a) {for(int i=0;i<a.length;i++)p(a[i]);};
	private static void p(long[] a) {p(Arrays.toString(a));};
	private static void p(int[][] a) {for(int i=0;i<a.length;i++)p(a[i]);};
	private static void p(int[] a) {p(Arrays.toString(a));};
	//大量にout.println();をすると、自動でout.flush();されるので、出力される順番には気を付けよう
	// * out.println()の後にSystem.out.println();をしたいときとかねー
	private static <T> void p(T t) {Systemout.println(t);}
	private static <T> void p() {Systemout.println();}
	private static void p(graph.edge2[] e) {
		for (int i = 0; i < e.length; i++) {
			Systemout.println(e[i].to+" "+e[i].cost);
		}
	}
	private static void doubleToString(double a) {
		System.out.println(BigDecimal.valueOf(a).toPlainString());
	}
	private static ArrayList<Map<Integer,Integer>> c;
	private static int ketawa(String S) {
		int res=0;
		for(int i=0;i<S.length();i++) {
			res+=S.charAt(i)-'0';
		}return res;
	}
	private static int ketawa(int S) {
		int res=0;
		while(S!=0) {
			res+=S%10;
			S/=10;
		}
		return res;
	}
	private static long X_x[]=new long[1];
	private static long kaijou(int x,long mod) {
		if(X_x.length!=300000)X_x=new long[300000];
		if(x<=1)return X_x[x]=1;
		if(X_x[x]!=0)return X_x[x];
		return X_x[x]=(x*kaijou(x-1,mod))%mod;
		/*long a=1;
		for(int i=2;i<=K;i++)a=(a*i)%mod;
		return (int)a;*/
	}

	static class segmentTree{
		int n;
		long dat[];
		long identity;//単位元
		segmentTree(int N,long identity) {//setTreeの要素の数,単位元
			this.identity =identity;
			init(N);
		}
		void init2() {
			Arrays.fill(dat, 0);
		}
		void init(int n_) {
			this.n=1;
			while(n<n_)n*=2;
			this.dat= new long[2*n-1];
			Arrays.fill(dat, identity);
		}
		void update(int k,long a) {
			k+=n-1;
			dat[k]=a;
			while(k>0) {
				k=(k-1)/2;
				//System.err.println("update "+k+" "+Cal(this.dat[k*2+1],this.dat[k*2+2]));
				dat[k]=Cal(dat[k*2+1],dat[k*2+2]);
			}
		}
		//外から呼び出すときはl=0,r=-1,k=0にする。
		void update(int a,int b,int k,int X,int l,int r) {
			if(r==-1)r=n;
			if(r<=a||b<=l) {
				return;
			}
			if(a<=l&&r<=b) {
				dat[k]=min(dat[k],X);
			}else {
				update(a, b, k*2+1,X, l, (l+r)/2);
				update(a, b,k*2+2,X,(l+r)/2,r);
			}
		}
		long get(int k) {//k番目の値を取得 0<=k<N
			k+=n-1;
			return dat[k];
		}
		//[a,b]を求める。
		//a~bのこと。0-indexed
		long getV(int a,int b) {
			a=Math.max(0,a);
			b=min(n-1,b);
			b++;
			return query(a, b, 0, 0, n);
		}
		int getleft(int a,int b,long x) {
			return find_leftest_sub(a, b, x, 0, 0, n);
		}
		int getright(int a,int b,long x) {
			return find_rightest_sub(a, b, x, 0, 0, n);
		}
		//[a,b)の値を求める
		//a~b-1のことで、0-indexed
		//外から呼び出すときは、a,b,0,0,N
		long query(int a,int b,int k,int l,int r) {
			if(r<=a||b<=l) {
				//l,rが求めたい区間a,bに完全に含まれていない
				return identity;
			}
			if(a<=l&&r<=b) {
				//l,rが、求めたい区間a,bに完全に含まれている
				return dat[k];
			}else {
				//l,rが、求めたい区間a,bに一部分だけ含まれている。
				long A=query(a, b, k*2+1, l, (l+r)/2);
				long B=query(a, b, k*2+2, (l+r)/2, r);
				return Cal(A,B);
			}
		}

		//x以下の要素を持つ最も左のもののindexを返す。 *RM(min)Q上でしか動かない
		 int find_rightest_sub(int a, int b, long x, int k, int l, int r) {
		        if (dat[k] > x || r <= a || b <= l) {  // 自分の値がxより大きい or [a,b)が[l,r)の範囲外ならreturn a-1
		            return a - 1;
		        } else if (k >= n - 1) {  // 自分が葉ならその位置をreturn
		            return (k - (n - 1));
		        } else {
		            int vr = find_rightest_sub(a, b, x, 2 * k + 2, (l + r) / 2, r);
		            if (vr != a - 1) {  // 右の部分木を見て a-1 以外ならreturn
		                return vr;
		            } else {  // 左の部分木を見て値をreturn
		                return find_rightest_sub(a, b, x, 2 * k + 1, l, (l + r) / 2);
		            }
		        }
		    }
		    int find_leftest_sub(int a, int b, long x, int k, int l, int r) {
		        if (dat[k] > x || r <= a || b <= l) {  // 自分の値がxより大きい or [a,b)が[l,r)の範囲外ならreturn b
		            return b;
		        } else if (k >= n - 1) {  // 自分が葉ならその位置をreturn
		            return (k - (n - 1));
		        } else {
		            int vl = find_leftest_sub(a, b, x, 2 * k + 1, l, (l + r) / 2);
		            if (vl != b) {  // 左の部分木を見て b 以外ならreturn
		                return vl;
		            } else {  // 右の部分木を見て値をreturn
		                return find_leftest_sub(a, b, x, 2 * k + 2, (l + r) / 2, r);
		            }
		        }
		    }
		    //RSQ上で動きます。
		int query2(long X) {
			int k=0;
			//ここでは、Σ[0,r]Ai=Xとなる最小のrを求めたい
			while(k*2+1<dat.length) {
				if(dat[k*2+1]>=X) {
					k=k*2+1;
				}else {
					X-=dat[k*2+1];
					k=k*2+2;
				}
			}
			return k-=n-1;
		}
		long Cal(long a,long b) {
			//計算アルゴリズム
			return a+b;
			//return a|b;
			//return max(a,b);
			//return gcd(a, b);
			//return a^b;
			//return Math.min(a, b);
		}
		int size() {
			//Nではないよ、配列の大きさを返す。
			return n;
		}
		//確認事項:Calとidentity
		//segmentTreeで宣言、initで初期化する。
		void toString(int n) {
			for(int i=0;i<n*2;i++) {
				System.err.print(dat[i]+" ");
			}
			System.err.println();
		}
	}
	static char[][] clone(char V[][]) {
		char RES[][]=new char[V.length][V[0].length];
		for (int i = 0; i < V.length; i++) {
			for (int t = 0; t < V[0].length; t++) {
				RES[i][t]=V[i][t];
			}
		}
		return RES;
	}
	static int[][] clone(int V[][]) {
		int RES[][]=new int[V.length][V[0].length];
		for (int i = 0; i < V.length; i++) {
			for (int t = 0; t < V[0].length; t++) {
				RES[i][t]=V[i][t];
			}
		}
		return RES;
	}

	static long ceil(long a,long b) {
		//ceil(a/b)を返す。
		//a/bの切り上げ
		return (a+b-1)/b;
	}
	static long floor(long a,long b) {
		//floor (a/b)を返す。
		//a/bの切り捨て
		return a/b;
	}
	//Math.multiplyExact(T, A[i])

	
	static int keta(long N) {
    	int a=1;
    	long b=10;
    	for(int i=0;i<17;i++) {
    		if(N<b)return a;
    		a++;b*=10;
    	}
    	return 18;
    }
	static final long mod7=Pow(10,9)+7,mod9=Pow(10,9)+9;
	static long LINF =(1L<<63)-1,count=0,sum=0,max=-LINF,min=LINF,ans=0,temp;
	static int i=0,INF=(1<<31)-1,compmax=0;
	static long A[];
	static final long mod998244353=998244353;
	static Random r=new Random();
	public static void main(String[] args) {
        new Thread(null, new Main(), "", 1024 * 1024 * 1024).start(); //16MBスタックを確保して実行
    }
    public void run() {
    	for(int i=0;i<1;i++) {
			solver();
			Systemout.close();
			Systemout.flush();
		}
    }
    
    
   
    
    static boolean eval(long key) {
    	Set<Integer> s=new HashSet<>();
    	return true;
    }
    static void addMap(Map<Long,Long> f,long key,long size) {
    	if(f.containsKey(key)) {
    		f.replace(key, f.get(key)+size);
    	}else {
    		f.put(key, size);
    	}
    }
    
    static boolean isOk(int x,int y,int N) {
    	
    	return x>=0&&y>=0&&x<N&&y<N;
    }
   
    static void revse(T A[]) {
    	for(int i=0;i<A.length/2;i++) {
    		swap(A, i, A.length-i-1);
    	}
    }
    private static void swap(T V[],int a,int b) {
		T temp=V[b];
		V[b]=V[a];
		V[a]=temp;
	}
   
    static void comp(int a[]) {
		binarySearch bs = new binarySearch();
		int b[]=a.clone();//速度重視
		//int b[] = Arrays.stream(a).distinct().toArray();
		//圧縮サイズ最小化重視
		Arrays.parallelSort(b);
		for (int i = 0; i < a.length; i++) {
			a[i]=bs.lowerBound(b, a[i])+1;
			compmax=Math.max(compmax,a[i]);
		}
	}
    static class edge implements Comparable<edge>{
    	int from,to,dis;
    	public edge(int from,int to,int dis) {
    		//v->u->w->vの辺
    		//count:種類数を表す。
    		this.from=from;
    		this.to=to;
    		this.dis=dis;
    	}
    	public int compareTo(edge o) {
			Comparable key = (Comparable)this.dis;
			Comparable key2 = (Comparable)o.dis;
			return key.compareTo(key2);
		}
    }
    
    /**
     * @a
     */
    
    private static void test(){
		//are
	}
    
    //psv:関数の宣言
    //tree:頂点0を根とする木を構築する
    //graph:グラフの入力受け取り
    //複数行の選択 Shift + ↑ or ↓
    //行単位のソースを移動する　[Alt + ↑ or ↓]
    //指定行の削除をする　[Ctrl + d]
    //12. 検索画面の表示 : Ctrl + F
    //変数名、メソッド名を一括変更する　[Alt + Shift + r]
    //r連打で画面が表示される(？)
    //new int[]{1,2}
    private static int partnum(int n,int k){
    	if (n == 1 || k == 1)return 1;
        if (n < 0 || k < 1)return 0;
        else return partnum(n - k, k) + partnum(n, k - 1);
	}
    private static int pn(int n){
    	return partnum(n, n);
	}
    static class T implements Comparable<T>{
    	int v;
    	long cost;
    	int index;
    	public T(int v,long cost,int index) {
    		this.v=v;
    		this.cost=cost;
    		this.index=index;
    	}
    	public int compareTo(T o) {
			Comparable key = (Comparable)this.cost;
			Comparable key2 = (Comparable)o.cost;
			return key.compareTo(key2);
		}
    }
    
    
    /*任意のエディタ上で Ctrl+Shift+D （または右クリック「codic」「クイックルック」）でネーミング生成します。
		日本語で検索するとネーミング生成、英語で検索すると英和（Codic English Dictionary) 検索します。
		フィードバックには対応していません。Webから行ってください。*/
    private static void solver(){
    	int T=sc.nextInt();
    	for(int i=0;i<T;i++) {
    		long L=sc.nextLong(),R=sc.nextLong();
    		//long L=r.nextInt(10000),R=L+r.nextInt(10000);
    		long r=0;//Rを全探索する
    		long ans=0;
    		for(int t=0;t<10;t++) {
    			r=r*10+9;
    			if(r<L)continue;
    			
    			long temp=query(L, min(r,R));
    			//System.out.println("ans:"+temp);
    			ans=max(ans,temp);
    			if(r>R)break;
    		}
    		Systemout.println(ans);
    		Systemout.flush();
    		//if(L>R)continue;
    		//if(query(L, R)==query2(L, R))continue;
    		//System.out.println(L+";"+R);
    		//System.out.println(query(L, R)+" "+query2(L, R));
    		//System.out.println("===========");
    	}
    	
    }
    private static long  query2(long L,long R){
    	
    	long a=1;
		while(a*10<R) {
			a*=10;
		}
		if(a<=L) {//ここ未チェック
			if(a*10==R)return (R-L);
			return (R-L+1);
		}
		
		//まず、a~Rの区間を%aと/10でmapに入れる。
		long ans=0;
		Set<Long> s=new HashSet<>();
		for(long i=R;i>=a;i--) {
			
			s.add(i%a);
			s.add(i/10);
			if(s.contains(i))continue;
			ans++;
		}
		for(long i=max(L,a/10);i<a;i++) {
			if(s.contains(i))continue;
			ans++;
			s.add(i);
		}
		return ans;
		
    	
    	
	}
    private static long query(long L,long R){
    	if(L>R)return 0;
		long a=1;
		while(a*10<R) {
			a*=10;
		}
		//System.out.println(L+" "+R+" "+a);
		if(R==a*10) {
			//この場合は難しい
			//-1か+0する必要がある。
			//L<=aならば-1
			//それ以外は+0
			long res=query(L, R-1);
			if(!(L<=a))res++;
			
			return res;
		}
		
		if(a<=L) {
			return (R-L+1);
		}
		if(R>=a*2-1) {
			return R-max(L,a)+1;
		}
		
			
		long res=0;
		res+=(R-a+1);
		//System.out.println("rest::"+res);
		
		long subL=max(L,(R+1)%a,a/10);
		long subR=(a*2-1)%a;
		if(subR<subL)return res;
		//10 11 12 13 14 15 16 17 18と9
		//[max(L,(R+1)%a),(a*2-1)%a]で、[a/10,R/10]でないものの数。
		//更に、[0,R%a]でないもの。
		long trashL=a/10;
		long trashR=R/10;
		//System.out.println(subL+";"+subR+" ::: "+trashL+";"+trashR);
		
		if(subL<=trashL&&trashR<=subR) {
			//完全に含まれる
			res+=(subR-subL+1)-(trashR-trashL+1);
		}
		else if((trashR<subL)||(subR<trashL)) {
			//全く含まれない
			res+=(subR-subL+1);
		}else if(subL<=trashR&&trashR<=subR){
			//右側だけ含まれる
			//[trashR+1,subR]
			res+=(subR-trashR);
		}else {
			//左側だけ含まれる
			//[subL,trashL-1]
			res+=(trashL-subL);
		}
		
		
		
		return res;
		
		
		
    
    	
	}
    private static long sol(int L,int R){
    	long ans=0;
		
		long V=1;
		while(V*10<=R)V*=10;
		//とりあえず一番上の桁のやつは採用するじゃん
		//[max(V,L),R]
		//次に、[max(V,L),R] % Vについて考える。
		//[V/10,V)について、[L,R]の中にあって、
		//[max(V,L),R]に出てこない物を数え上げたい
		if(L>=V&&R>=V) {
			return (R-L+1);
		}
		ans+=(R-V+1);
		//ans>=Vの時、その時点で終わり
		if(R>=2*V) {
			return ans ;
		}
		
		//[V-1 ~ V/10)を考える
		//[V-1,max(V/10,T))
		ans+=((V-1)-max(V/10,R%V,L-1,min(V/10-1,R%V)+V/10));
		return ans;
	}
    private static String make(char a, char b, char c,int N,int g){
    	StringBuffer res = new StringBuffer();
		for(int i=0;i<N;i++) {
			
			if(i==g)res.append(c);
			else res.append(a);
		}
		res.append(b);
		for(int i=0;i<N;i++) {
			
			if(i==0)res.append(a);
			else res.append(c);
		}
		//System.out.println(res.toString());
		return res.toString();
	}
    static boolean temp_flag=false;
    private static boolean test(String f,String T,String T2,String T3){
		if(unvisible_test(f, T)&&unvisible_test(f, T2)&&unvisible_test(f, T3)&&!temp_flag) {
			temp_flag=true;
			System.out.println(f);
		}
    	return unvisible_test(f, T)&&unvisible_test(f, T2)&&unvisible_test(f, T3);
	}
    private static boolean unvisible_test(String f,String T){
		//fがTの部分列であるか判定する
    	int now=0;
    	int i=0;
    	while(now<f.length()&&i<T.length()) {
    		if(f.charAt(now)==T.charAt(i))now++;
    		i++;
    	}
    	return now==f.length();
	}
    static double tripet(int max,int min,long A[]) {
		for(int i=0;i<10;i++){
			int c1=(max-min)/3+min;
			int c2=(max-min)*2/3+min;
			double x1=A[c1]*1.0/2;
			double x2=A[c2]*1.0/2;
			//System.out.println(max+" "+min);
			//System.out.println(F(x1,A)+" "+F(x2,A));
			if(F(x1,A)>=F(x2,A)) {
				min=c1;
			}else{
				max=c2;
			}
			
		}
		//System.out.println((max+min)/2);
		return F(A[(max+min)/2]*1.0/2,A);
	}
	static double F(double x,long A[]) {
		//この関数を最小化したい
		double res=0;
		for(int i=0;i<A.length;i++) {
			res+=(x+A[i])-Math.min(2*x,A[i]);
		}
		return res;
	}
	static double tripet(double x,double max,double min) {
		while(max-min>=0.0000000000001){
			double c1=(max-min)/3+min;
			double c2=(max-min)*2/3+min;
			if(F(x,c1)>=F(x,c2)) {
				min=c1;
			}else{
				max=c2;
			}
		}
		return (max+min)/2;
	}
	static double F(double x,double y) {
		return x+y;
	}
    private static long f(int now,long cost[],int flag[],ArrayList<ArrayList<Integer>> cc){
    	long res=0;
		for(int to:cc.get(now)) {
			if(flag[to]==1)continue;
			flag[to]=1;
			res+=f(to,cost,flag,cc);
		}
    	return res+cost[now];
	}
    static void solv2() {
    	int N=sc.nextInt();
    	String S[]=new String[N];
    	for(int i=0;i<N;i++) {
    		S[i]=sc.next();
    	}
    	Scanner sc=new Scanner(System.in);
    	for(int i=0;i<N;i++) {
    		if(eval(S[i],S[i].charAt(0)))continue;
    		for(int j=0;j<N;j++) {
    			if(i==j)continue;
    			if(eval(S[j],S[j].charAt(0)))continue;
    			if(S[i].charAt(0)==S[j].charAt(0)) {
    				System.out.println("Case #"+(ans++)+": IMPOSSIBLE");
    				return;
    			}
    			if(S[i].charAt(S[i].length()-1)==S[j].charAt(S[j].length()-1)) {
    				
    				System.out.println("Case #"+(ans++)+": IMPOSSIBLE");
    				return;
    			}
    		}
    	}
    	//あとはつなげていって最後に判定する
    	for(int i=0;i<N;i++) {
    		if(S[i].length()==0)continue;
    		if(!eval(S[i],S[i].charAt(0)))continue;
    		for(int j=0;j<N;j++) {
    			if(i==j||S[j].length()==0)continue;
    			if(S[i].charAt(0)==S[j].charAt(S[j].length()-1)) {
    				S[i]=S[j]+S[i];
    				S[j]="";
    			}
    		}
    	}
    	for(int i=0;i<N;i++) {
    		if(S[i].length()==0)continue;
    		for(int j=0;j<N;j++) {
    			if(i==j||S[j].length()==0)continue;
    			if(S[i].charAt(0)==S[j].charAt(S[j].length()-1)) {
    				S[i]=S[j]+S[i];
    				S[j]="";
    			}
    		}
    	}
    	StringBuffer res = new StringBuffer();
		for(int i=0;i<N;i++)res.append(S[i]);
		Set<Integer> s=new HashSet<>();
		for(int i=0;i<res.length();) {
			char V=res.charAt(i);
			if(s.contains(V+0)) {
				System.out.println("Case #"+(ans++)+": IMPOSSIBLE");
				return;
			}
			s.add(V+0);
			while(i<res.length()&&res.charAt(i)==V)i++;
		}
		System.out.println("Case #"+(ans++)+" "+res.toString());
    	
    }
    static boolean eval2(String S,char c) {
    	int s=0;
    	while(s<S.length()&&S.charAt(s)==c)s++;
    	if(s==S.length())return false;
    	while(s<S.length()) {
    		if(S.charAt(s)==c)return true;
    		s++;
    	}
    	return false;
    	
    }
    static boolean eval(String S,char c) {
    	for(int i=0;i<S.length();i++) {
    		if(S.charAt(i)!=c)return false;
    	}
    	return true;
    }
    static String F(int n) {
    	StringBuffer S = new StringBuffer();
    	for(int i=0;i<n;i++) {
    		S.append("0");
    	}
    	return S.toString();
    }
    static void mol() {
    	int bit=15;
    	oneMax O=new oneMax(bit, 100);
    	int rep=10;
    	long S=0;
    	for(int i=0;i<rep;i++) {
    		long score=O.eval(S);
    		//System.out.println(S+" score:"+score);
    		S=O.HC(S);
    	}
    	long best=S;
    	long bestS=O.eval(S);
    	for(int i=0;i<(1L<<bit);i++) {
    		long score=O.eval(i);
    		if(bestS<score) {
    			System.out.println(bestS+" "+"SCORE:"+score+" "+i);
    		}
    	}
    }
    static class oneMax{
    	long V[];
    	int bit;
    	public oneMax(int bit,int size) {
			this.bit=bit;
			init(size);
		}
    	private void init(int size) {
    		V=new long[size];
    		Random r=new Random();
    		for(int i=0;i<size;i++) {
    			long T=Math.abs(r.nextLong());
    			if(bit!=63) {
    				T%=(1L<<bit);
    			}
    			V[i]=T;
    		}
    		System.out.println(Arrays.toString(V));
		}
    	long eval(long A) {
    		return evalB(A);
    	}
    	private long evalA(long A) {
    		//bit桁の解Aの評価値を返す。
    		return Long.bitCount(A);
    	}
    	private long evalB(long A) {
    		//bit桁の解Aの評価値を返す。
    		if(!isPossible(A))return -1;//実行不可能解の場合は-1を返す。
    		long score=0;
    		for(int t=0;t<V.length;t++) {
    			long temp=0;//同じbitの数。
				for(int i=0;i<bit;i++) {
					if(((V[t]>>i)&1)==((A>>i)&1)) {
						temp++;
					}
				}
				score+=temp;
				//System.out.println(" "+A+" "+t+" "+temp);
			}
    		return score;
    		
    	}
    	long HC(long N) {
        	//近傍解を取得し、遷移する。
        	long local_best=N;
        	long local_best_score=eval(N);
        	for(int i=0;i<bit;i++) {
        		//Nのiビット目を反転する。(近傍解の列挙)
        		long newer=N^(1L<<i);
        		long score=eval(newer);
        		//最もスコアの高い近傍を記録する。
        		if(score>local_best_score) {
        			local_best_score=score;
        			local_best=newer;
        		}
        	}
        	return local_best;
        }
    	boolean isPossible(long A) {
    		//Aが制約を満たすか確かめる。
    		return A>=0&&(bit==63||A<(1L<<bit));
    	}
    	
    	
    }
    static long oneMax_HC_bad2(long N,int bit,Random r,int rMax) {
    	//悪い山登り法を作ってみる。
    	//近傍解を適当に定義する。
    	//randomな値を足した解を近傍解とする。
    	//rMax:randomに取る値の区間[rMin,rMax)
    	long local_best=N;
    	int local_best_score=oneMax_eval(N, bit);
    	
    	for(int i=0;i<100;i++) {
    		long V=N^(r.nextInt(rMax));
    		int score=oneMax_eval(V, bit);
    		if(score>local_best_score) {
    			local_best_score=score;
    			local_best=V;
    		}
    	}
    	return local_best;
    }
    static long oneMax_HC_bad(long N,int bit,Random r,int rMax) {
    	//悪い山登り法を作ってみる。
    	//近傍解を適当に定義する。
    	//randomな値を足した解を近傍解とする。
    	//rMax:randomに取る値の区間[rMin,rMax)
    	long local_best=N;
    	int local_best_score=oneMax_eval(N, bit);
    	int t=1;
    	for(int i=0;i<1000;i++) {
    		long V=N+t*r.nextInt(rMax);t*=-1;
    		int score=oneMax_eval(V, bit);
    		if(score>local_best_score) {
    			local_best_score=score;
    			local_best=V;
    		}
    	}
    	return local_best;
    }
    static boolean isPossible(long N,int bit) {
    	//実行可能解かチェックする。
    	return N>=0&&N<(1L<<bit);
    	
    }
    static long oneMax_HC(long N,int bit) {
    	//近傍解を取得し、遷移する。
    	long local_best=N;//best:最もスコアの高い近傍
    	int local_best_score=oneMax_eval(N, bit);//best_score:最もスコアの高い近傍のスコア
    	for(int i=0;i<bit;i++) {
    		//Nのiビット目を反転する。(近傍解の列挙)
    		//T:近傍解
    		long newer=N^(1L<<i);
    		int score=oneMax_eval(newer, bit);
    		
    		//最もスコアの高い近傍を記録する。
    		if(score>local_best_score) {
    			local_best_score=score;
    			local_best=newer;
    		}
    	}
    	return local_best;
    }
    static int oneMax_eval(long N,int bit) {
    	//N:解
    	//bit:解のビット数
    	//制約条件:N<(1<<bit) (⇔Nのbit数がbitである)
    	//-返り値-
    	//非実行可能解の場合:-1を返す。
    	if(!isPossible(N, bit))return -1;
    	return Long.bitCount(N);
    }
    static int popCount(int n) {
		int cnt = (n & 0x55555555) + (n >> 1 & 0x55555555);
		cnt = (cnt & 0x33333333) + (cnt >> 2 & 0x33333333);
		cnt = (cnt + (cnt >> 4)) & 0x0f0f0f0f;
		cnt = cnt + (cnt >> 8);
		cnt = cnt + (cnt >> 16);
		return cnt & 0x7f;
	}
    static long eval(int S,int L) {
    	return modPow(S, L, mod998244353);
    }
    static void print(int V[][]) {
    	for(int i=0;i<V.length;i++) {
    		System.out.println(Arrays.toString(V[i]));
    	}
    }
    static String getOrder(int fx,int fy,int tx,int ty,int k,double p) {
    	StringBuffer res = new StringBuffer();
    	char V='U';
    	int count=0;
    	if(fx==tx) {
    		count=Math.abs(ty-fy);
    		if(fy<ty) {
    			V='R';
    		}else {
    			V='L';
    		}
    	}
    	if(fy==ty) {
    		count=Math.abs(fx-tx);
    		if(fx<tx) {
    			V='D';
    		}else {
    			V='U';
    		}
    	}
    	int v=count;
    	for(int i=0;i<k;i++) {
    		double score=f(p, count, v);
    		if(score>=0.99)break;
    		v++;
    		
    	}
    	for(int i=0;i<v;i++) {
    		res.append(V);
    	}
    	return res.toString();
    }
    static double f(double p,int k,int N) {
    	//試行回数nで、(1-p)の確率で+1でk以上になる確率
    	double res[]=new double[k+1];
    	res[0]=1;
    	for(int i=0;i<N;i++) {
    		double newer[]=new double[k+1];
        	for(int t=0;t<k;t++) {
        		newer[t+1]+=res[t]*(1-p);
        		newer[t]+=res[t]*p;
        	}
        	newer[k]+=res[k];
        	for(int t=0;t<newer.length;t++) {
        		res[t]=newer[t];
        	}
    	}
    	return res[k];
    }
    static class V implements Comparable<V>{
    	int v,dis,f;
    	public V(int v,int dis) {
    		//v->u->w->vの辺
    		//count:種類数を表す。
    		this.v=v;this.dis=dis;
    	}
    	public V(int v,int dis,int f) {
    		//v->u->w->vの辺
    		//count:種類数を表す。
    		this.v=v;this.dis=dis;
    		this.f=f;
    	}
    	public int compareTo(V o) {

			Comparable key = (Comparable)this.dis;
			Comparable key2 = (Comparable)o.dis;
			return key.compareTo(key2);
		}
    }
   
    static long sqrt(long X) {
    	long s=0,e=3037000499L;
    	while(true) {
    		long g=(s+e)/2;
    		if(g*g<=X) {
    			s=g;
    		}else {
    			e=g;
    		}
    		if((s+e)/2==g)break;
    	}
    	return (s+e)/2;
    }
    static long cal(int V[],long F,int N) {
    	long res=0;
    	for(int i=0;i<V.length;i++) {
    		if(((F>>i)&1)==1) {
    			res+=(1L<<i)*(N-V[i]);
    		}else {
    			res+=(1L<<i)*V[i];
    		}
    		
    	}
    	return res;
    }
    static void show(int V[]) {
    	StringBuffer S = new StringBuffer();
    	for(int i=0;i<V.length;i++) {
    		S.append(V[i]+" ");
    	}
    	System.out.println(S.toString());;
    }
    static long mal(long a,long b,long mod) {
    	a%=mod;b%=mod;
    	long f=a*b;
    	f%=mod;
    	return f*modinv(2, mod);
    }
    static long getCost(long hx,long hy) {
    	if(hx>hy) {
    		return hx-hy;
    	}
    	if(hx==hy) {
    		return 0;
    	}
    	return -2*(hy-hx);
    }
    static void swap(char a[],int s,int t) {
    	char temp=a[s];
    	a[s]=a[t];
    	a[t]=temp;
    }
    static boolean g(long a,long b,long mod) {
    	a=(a%mod+mod)%mod;
    	b=(b%mod+mod)%mod;
    	return a==b;
    }
    static void reverse(int A[]) {
    	int B[]=new int[A.length];
    	for(int i=0;i<A.length;i++) {
    		A[i]=B[A.length-i-1];
    	}
    	A=B.clone();
    }
    static ArrayList<Long> divide(long T){
    	ArrayList<Long> cc=new ArrayList<>();
    	for(long i=1;i*i<=T;i++) {
    		if(T%i==0) {
    			cc.add(i);
    			if(T/i!=i)cc.add(T/i);
    		}
    	}
    	return cc;
    }
     static boolean check(int x,int y,int H,int W) {
    	if(x>=H||x<0||y<0||y>=W)return false;
    	return true;
    }
    static boolean check(char S[][],int X,int Y,int flag[][],int a) {
    	if(flag[X][Y]!=0||S[X][Y]=='.')return false;
    	Deque<Integer> x=new ArrayDeque<>();//push後ろに入れる,poll(pop)後ろからとる,peek addは先頭に入るからバグ注意
    	Deque<Integer> y=new ArrayDeque<>();//push後ろに入れる,poll(pop)後ろからとる,peek addは先頭に入るからバグ注意
    	x.add(X);y.add(Y);
    	int V[]= {0,0,-1,1};
    	flag[X][Y]=a;
    	int N=S.length;
    	while(!x.isEmpty()) {
    		X=x.poll();
    		Y=y.poll();
    		for(int i=0;i<4;i++) {
    			if(X+V[i]<0||X+V[i]>=N||Y+V[(i+2)%4]<0||Y+V[(i+2)%4]>=N||flag[X+V[i]][Y+V[(i+2)%4]]!=0||S[X+V[i]][Y+V[(i+2)%4]]=='.')continue;
    			x.add(X+V[i]);y.add(Y+V[(i+2)%4]);
    			flag[X+V[i]][Y+V[(i+2)%4]]=a;
    		}
    	}
    	return true;
    }
    
    static void solv3(int cs[],int ct[]) {
    	int ninesS[]=new int[10];
    	int ninesT[]=new int[10];
    	int s=1,t=8;
    	while(s<10) {
    		if(cs[s]<=0||ct[t]<=0) {
    			s++;t--;continue;
    		}
    		ninesS[s]=min(cs[s],ct[t]);
    		ninesT[t]=min(cs[s],ct[t]);
    		cs[s]-=min(cs[s],ct[t]);ct[t]-=min(cs[s],ct[t]);
    	}
    	int use_s=-1,use_t=-1;
    	
    	//tの9を使わない場合
    	s=9;
    	a:while(s>0) {
    		t=1;
    		while(t<9) {
    			if(cs[s]<=0||ct[t]<=0) {
    				t++;continue;
    			}
    			use_s=s;use_t=t;cs[s]--;ct[t]--;break a;
    		}
    	}
    	//tの9を使う場合
    	if(use_s==-1) {
    		if(ct[9]>0) {
    			s=9;
    			while(s>0) {
    				if(cs[s]>0) {
    					cs[s]--;ct[9]--;
    					use_s=s;use_t=9;break;
    				}
    			}
    		}
    	}
    	//9のペアを崩す場合
    	if(use_s==-1) {
    		s=9;
        	while(s>0&&cs[s]<=0)s--;
        	use_s=s;
        	t=10-s;
        	while(t<10&&ninesT[t]<=0)t++;
        	use_t=t;
        	if(s==0||t==10)use_s=-1;
        	else {
        		cs[s]--;ninesT[t]--;ninesS[9-t]--;
        		cs[9-t]++;
        	}
    	}
    	if(use_s==-1) {
    		t=9;
        	while(t>0&&ct[t]<=0)t--;
        	use_t=t;
        	s=10-t;
        	while(s<10&&ninesS[s]<=0)s++;
        	use_s=s;
        	if(t==0||s==10)use_s=-1;
        	else {
        		ct[t]--;ninesS[s]--;ninesT[9-s]--;
        		ct[9-s]++;
        	}
    	}
    	if(use_s==-1) {
    		t=9;
        	while(t>0&&ninesT[t]<=0)t--;
        	use_t=t;
        	s=10-t;
        	while(s<10&&ninesS[s]<=0)s++;
        	use_s=s;
        	if(t==0||s==10)use_s=-1;
        	else {
        		ninesT[t]--;ninesS[9-t]--;ninesS[s]--;ninesT[9-s]--;
        		//これ余ったやつ戻さないとじゃね
        		cs[9-t]++;ct[9-s]++;
        	}
    	}
    	
    	StringBuffer S = new StringBuffer();
    	StringBuffer T = new StringBuffer();
    	
    	//まず、10をくっつける作業
    	if(use_s!=-1) {
    		S.append((char)('0'+use_s));
    		T.append((char)('0'+use_t));
    	}
    	//次に9をくっつける作業
    	int a=1;
    	while(a<10) {
    		if(ninesS[a]==0) {
    			a++;continue;
    		}
    		S.append((char)('0'+a));
    		T.append((char)('0'+9-a));ninesS[a]--;
    	}
    	
    	
    	//余ったやつをくっつける作業,ctは少し余るかもしれない
    	//10のマッチングうまくやって無くない？？？？？？？？？？？？？？
    	int b=8;a=9;
    	//優先順位はbの大きいやつ
    	while(b>0) {
    		a=10-b;
    		while(ct[b]>0&&a<10) {
    			if(cs[a]<=0) {
    				a++;continue;
    			}
    			while(ct[b]>0&&cs[a]>0) {
    				S.append((char)('0'+a));
    	    		T.append((char)('0'+b));
    				cs[a]--;ct[b]--;
    			}
    			
    			
    			
    			
    			
    		}
    		
    		b--;
    	}
    	
    	
    }
    static void secondSolv(int cs[],int ct[],int ans_s[],int ans_t[]) {
    	StringBuffer S = new StringBuffer();
    	StringBuffer T = new StringBuffer();
    	
    	int a=1,b=1;
    	while(a<10&&b<10) {
    		if(cs[a]==0){
    			a++;continue;
    		}
    		if(ct[b]==0) {
    			b++;
    			continue;
    		}
    		while(cs[a]!=0&&ct[b]!=0) {
    			S.append((char)(a+'0'));
    			T.append((char)(b+'0'));
    			cs[a]--;ct[b]--;
    		}
    	}
    	//とりあえず要らないやつを付け終わったよ
    	int i=ans_s.length-1;
    	while(i>=0) {
    		if(ans_s[i]==0) {
    			i--;continue;
    		}
    		S.append((char)(ans_s[i]+'0'));
    		T.append((char)(ans_t[i]+'0'));
    		i--;
    	}
    	b=9;
    	while(b>0) {
    		while(ct[b]>0) {
    			ct[b]--;
    			T.append((char)(b+'0'));
    		}
    		b--;
    	}
    	//これで後ろも付け終わったよ
    	System.out.println(S.toString());
    	System.out.println(T.toString());
    	
    	
    	
    }
    static void count(String S,int c[]) {
    	for(int i=0;i<S.length();i++) {
    		c[S.charAt(i)-'0']++;
    	}
    }
    static void rev(char t[]) {
    	char temp[]=new char[t.length];
    	int a=0;
    	for(int i=t.length-1;i>=0;i--) {
    		temp[a++]=t[i];
    	}
    	for(int i=0;i<t.length;i++) {
    		t[i]=temp[i];
    	}
    }
    static int get(int a,int b,int c,int d,int V[][]) {
    	//(a,b)~(c,d)の区間を求める
    	return V[c][d]-V[c][b-1]-V[a-1][d]+V[a-1][b-1];


    }
    static void add(int x,int y,long data,long DP[][],int H,int W,int V[][]) {
    	if(x<0||y<0||x>=H||y>=W)return;
    	//System.out.println("add:"+x+" "+y+" ~"+data);
    	DP[x][y]+=data;
    	DP[x][y]%=mod998244353;
    }
    static  void opt2(int tourList[],int N,int Dis[][]) {
        boolean back=true;
        int i0=0;
        a:while(back) {
            back=false;
            for(int i=i0;i<i0+N;i++) {
                for(int j=i+2;j<i+N-1;j++) {
                    int a=tourList[i%N],b=tourList[(i+1)%N],c=tourList[j%N],d=tourList[(j+1)%N];

                    //if(Dis[a][b]+Dis[c][d]>Dis[a][d]+Dis[b][c]) {
                    if(Dis[a][b]+Dis[c][d]>Dis[a][c]+Dis[b][d]) {
                    for (int k = 0; k < (j-i)/2; k++) {
                            int temp = tourList[(i+1+k)%N];
                            tourList[(i+1+k)%N] = tourList[(j-k)%N];
                            tourList[(j-k)%N] = temp;
                        }
                        i0=(i+1)%N;
                        back=true;
                        continue a;
                    }

                    //i->i+1, j->j+1を
                    //i->j+1,j->i+1につなぎ直す事を考える。
                }
            }
        }
    }

    static boolean is_swappable(int arrayments[],int a,int b) {
    	a=arrayments[a];
    	b=arrayments[b];
    	if(a==b||a==-b)return false;
    	//a->bに出来るか。
    	//1-indexed
    	int s=index(arrayments, a);
    	int e=index(arrayments, b);
    	if(a<0&&e>s)return true;
    	if(a>0&&s>e)return true;

    	int target=index(arrayments, -a);
    	if(a<0&&target<e)return true;
    	if(a>0&&target>e)return true;
    	return false;

    }
    static int index(int arrayments[],int a) {
    	for(int i=0;i<arrayments.length;i++) {
    		if(arrayments[i]==a)return i;
    	}
    	return -1;
    }
	static long tsp_solv(int N,long cost[][]) {
		int start=0;
		long DP[][]=new long[N][1<<N];
		for(int i=0;i<N;i++)Arrays.fill(DP[i], LINF);
		
		//スタートの初期化,スタートの位置は本質ではない
		DP[start][1<<start]=0;

		for(int T=1;T<(1<<N);T++) {
			//Tは既に通った頂点の集合を表している。
			for(int from=0;from<N;from++) {
				if(((T>>from)&1)==0||DP[from][T]==LINF)continue;//まだfromに到達していない
				for(int to=0;to<N;to++) {
					if(((T>>to)&1)==1)continue;//既にtoに到達している
					//from->toへの移動を考える
					if(cost[from][to]==LINF)continue;
					DP[to][T|1<<to]=Math.min(DP[from][T]+cost[from][to],DP[to][T|1<<to]);
				}
			}
		}
		long res=LINF;
		for(int i=0;i<N;i++) {
			res=Math.min(res,DP[i][(1<<N)-1]+cost[i][start]);
		}
		return res;
	}
	static void swap(int A[],int a,int b) {
		int temp=A[a];
		A[a]=A[b];
		A[b]=temp;
	}
	static int v[]= {0,0,1,-1};
	static int sum(int i,int G[]) {
		int res=0;
		for(int t=0;t<G.length;t++) {
			if(((i>>t)&1)==0)continue;
			res+=G[t];
		}
		return res;
	}
	static boolean check(char V[][],int x,int y,int vx,int vy,int d) {
		for(int i=0;i<d;i++) {
			if(x<0||y<0||x>=V.length||y>=V[0].length)return false;
			if(V[x][y]=='#')return false;
			x+=vx;y+=vy;
		}
		return true;
	}

	
	static boolean check(char S[][],char T[][],int a,int b) {
		a*=-1;b*=-1;
		int N=T.length;
		for(int i=0;i<N;i++) {
			for(int t=0;t<N;t++) {
				if((a+i>=N||a+i<0||b+t<0||b+t>=N)&&S[i][t]=='#'){
						return false;
				}
				if((a+i>=N||a+i<0||b+t<0||b+t>=N))continue;
				if(S[i][t]!=T[i+a][b+t]&&(S[i][t]=='#'||T[a+i][b+t]=='#')) {
					return false;
				}

			}
		}
		return true;

	}
	static void nine(char V[][]) {
		char G[][]=clone(V);
		int N=V.length;
		int a=0,b=0;
		for(int i=N-1;i>=0;i--) {
			for(int t=0;t<N;t++) {
				V[i][t]=G[a++][b];
			}
			a=0;b++;
		}



	}
	static char[][] idou(char V[][],int a,int b) {
		int N=V.length;
		char G[][]=new char[N][N];
		for(int i=0;i<N;i++) {
			for(int t=0;t<N;t++) {
				if(i+a<N&&t+b<N&&i+a>=0&&t+b>=0)G[i+a][t+b]=V[i][t];
			}
		}
		return G;

	}
	static ArrayList<ArrayDeque<Integer>> cc = new ArrayList<>();
	static Map<Integer, Integer> m = new HashMap<>();;
	static void add(int index) {
		if(cc.get(index).size()==0)return;
		int color=cc.get(index).pollFirst();
		if(!m.containsKey(color)) {
			m.put(color, index);
		}else {
			add(m.get(color));
			add(index);
		}

	}
	static void makeT(ArrayList<ArrayList<Integer>> cc,int parent[],int root) {
		Arrays.fill(parent, -2);
		parent[root]=-1;
		Queue<Integer> now = new ArrayDeque<>(); //add,poll,peek BFSは前から実行される
		now.add(root);
		while(!now.isEmpty()) {
			int FROM=now.poll();
			for(int TO:cc.get(FROM)) {
				if(parent[TO]!=-2)continue;
				parent[TO]=FROM;
				now.add(TO);
			}
		}




	}
	
	static class CUM2{
		int V[][];
		int h,w;
		public CUM2(int A[][]) {//1-indexed
			init(A);
		}
		void init(int A[][]) {
			this.h=A.length;this.w=A[0].length;
			V=new int[h][w];
			for (int i = 0; i < h; i++) {
				V[i]=A[i].clone();
			}
			for (int i = 1; i <h; i++) {
				for (int t =1; t <w; t++) {
					V[i][t]+=V[i][t-1];
				}
			}
			for (int i = 1; i <w; i++) {
				for (int t = 1; t <h; t++) {
					V[t][i]+=V[t-1][i];
				}
			}
		}
		int get_sum(int a,int b,int c,int d) {
			//[a,b]~[c,d]の区間和を返す。1-indexed
			return V[c][d]+V[a-1][b-1]-V[a-1][d]-V[c][b-1];
		}
	}
	
	/*long kruskal(int V,edge es[]) {
		unionFind uf = new unionFind(V);
		Arrays.sort(es);
		long res=0;
		for (int i = 0; i < es.length; i++) {
			if(uf.find( es[i].from)!=uf.find( es[i].to)) {
				uf.union(es[i].from, es[i].to);
				res+=es[i].cost;
			}
		}
		return res;
	}*/
	//関数Fについて、区間[a,b]の最小値を求める

	static long getF(long a,long b,long c,long d) {
		//[a,b]と[c,d]の区間数の和


		if(b<c||a>d) {
			//完全に含まれない。
			return (b-a+1)+(d-c+1);
		}
		if(c<=a&&b<=d) {
			//完全に含まれる1
			return d-c+1;
		}
		if(a<=c&&d<=b) {
			//完全に含まれる2
			return b-a+1;
		}

		//一部だけ含まれる。
		if(c<=b&&b<=d) {
			//[c,b]が含まれる。
			return d-a+1;
		}
		if(c<=a&&a<=d) {
			return b-c+1;
		}
		return -1;

	}



	static boolean check(int HW[][],int i,int t,int W) {
		while(t<W) {
			if(HW[i][t]%2==1)return true;
			t++;
		}
		return false;
	}

	static void SCC(Map<Integer,ArrayList<Integer>> m,int N) {
		int BACK[]=new int[N];
		Arrays.fill(BACK, -1);

		for (int i = 0; i < N; i++) {
			if(BACK[i]!=-1)continue;
			getBackQuery(m, i, BACK);
			BACK[BACK_COUNT]=i;
		}
		Map<Integer,ArrayList<Integer>> reversedm=new HashMap<>();
		for(int Vex:m.keySet()) {
			for(int TO:m.get(Vex)) {
				if(!reversedm.containsKey(TO))reversedm.put(TO, new ArrayList<>());
				reversedm.get(TO).add(Vex);
			}
		}
		uf=new unionFind(N);
		for (int i = N-1; i>=0;i--) {
			//iを始点として、DFSを行う。到達可能マスが同じグループ
			if(uf.get(i)!=i)continue;
			sccquery(reversedm, i);
		}
	}
	static void sccquery(Map<Integer,ArrayList<Integer>> reversedm,int vex) {
		if(!reversedm.containsKey(vex)||reversedm.get(vex).size()==0)return;
		for(int TO:reversedm.get(vex)) {
			if(uf.find(vex)==uf.find(TO))continue;
			uf.union(vex, TO);
			sccquery(reversedm, vex);
		}
	}
	static int BACK_COUNT;
	static unionFind uf;
	static void getBackQuery(Map<Integer,ArrayList<Integer>> m,int Vex,int BACK[]) {
		if(!m.containsKey(Vex)||m.get(Vex).size()==0)return;
		for(int TO:m.get(Vex)) {
			if(BACK[TO]!=-1)continue;
			BACK[TO]=-2;
			getBackQuery(m, Vex, BACK);
			BACK[BACK_COUNT++]=TO;
		}
	}


	static ArrayList<Integer> Vs;
	static void getTopo(Map<Integer,ArrayList<Integer>> m,int N) {
		boolean flag[]=new boolean[N];
		Arrays.fill(flag, false);
		Vs=new ArrayList<>();
		for(int V:m.keySet()) {
			if(flag[V])continue;
			flag[V]=true;
			topoQuery(m, V, flag);
			Vs.add(V);
		}
		Collections.reverse(Vs);
	}
	static void topoQuery(Map<Integer,ArrayList<Integer>> m,int Vex, boolean flag[]) {
		//Vexからスタート
		//これ、閉路がある時に対応できてなくね
		if(!m.containsKey(Vex)||m.get(Vex).size()==0)return;
		for(int to:m.get(Vex)) {
			if(flag[to])continue;
			flag[to]=true;
			topoQuery(m, to,flag);
			Vs.add(to);


		}

	}
	static class Flow{
		static class edge{
			int to,cap,rev;
			public edge(int to,int cap,int rev) {
				// TODO 自動生成されたコンストラクター・スタブ
				this.to=to;
				this.cap=cap;
				this.rev=rev;
			}
		}

		public Flow(int N) {
			// TODO 自動生成されたコンストラクター・スタブ
			this.N=N;//頂点数
			init();
		}
		void init() {
			used=new boolean[N];
			G=new ArrayList<>();
			for (int i = 0; i < N; i++) {
				G.add(new ArrayList<>());
			}
		}
		int N;
		ArrayList<ArrayList<edge>> G;//iがfromを意味する 隣接リスト表現
		boolean used[];
		//from->toへ向かう容量capの辺をグラフに追加する。
		void add_edge(int from,int to,int cap) {
			G.get(from).add(new edge(to, cap, G.get(to).size()));
			G.get(to).add(new edge(from, 0, G.get(from).size()-1));
		}
		//最大流を求める 最悪計算量はO(F|E|) Fは流量,Eは辺の数?
		int max_flow(int s,int t) {
			int flow=0;
			while(true) {
				Arrays.fill(used, false);
				int f=dfs(s,t,INF);
				if(f==0)return flow;
				flow+=f;
			}
		}
		int dfs(int v,int t,int f) {
			if(v==t)return f;//tに到着したら終了
			used[v]=true;//vに訪れたことを表す
			for (int i = 0; i < G.get(v).size(); i++) {
				edge e=G.get(v).get(i);
				if(used[e.to]||e.cap<=0)continue;
				int d=dfs(e.to, t, Math.min(f,e.cap));
				if(d>0) {
					e.cap-=d;
					G.get(e.to).get(e.rev).cap+=d;
					return d;
				}
			}
			return 0;
		}
		//デバッグ用
		void get_edges(int T) {
			//頂点Tから出る辺を出力する
			int cout=0;
			for(edge e:G.get(T)) {
				System.out.println(cout+++" "+T+"=>"+e.to+" "+e.cap);
			}
		}
	}
	static class LCA{
		static class edge{
			int to;
			long cost;
			public edge(int to,long cost) {
				// TODO 自動生成されたコンストラクター・スタブ
				this.to=to;
				this.cost=cost;
			}
		}
		int N;//頂点の数(頂点名は、0-indexで命名)
		long dist[];//rootから頂点iまでの距離
		int root;//木の根
		int parents[];//頂点iの親がparents[i]
		int doubling[][];
		boolean is_built;
		ArrayList<ArrayList<edge>> T;
		public LCA(int N,int root) {
			//頂点数と根を受け取る。
			is_built=false;
			this.root=root;
			this.N=N;
			T=new ArrayList<>(N);
			for (int i = 0; i < N; i++) {
				T.add(new ArrayList<>());
			}
		}
		void add_edge(int u,int v,long cost) {
			T.get(u).add(new edge(v,cost));
			T.get(v).add(new edge(u,cost));
		}
		void build() {
			init();
			is_built=true;
		}
		void init() {
			parents=new int[N];
			dist=new long[N];
			doubling=new int[31][N];
			dfs(T);
			init_doubling();
		}
		void dfs(ArrayList<ArrayList<edge>> T) {
			//根からの距離と親を求める。
			boolean flag[]=new boolean[N];
			Arrays.fill(flag, false);
			flag[root]=true;
			parents[root]=root;
			Queue<Integer> qq = new ArrayDeque<>(); //始点を保存
			qq.add(root);
			while(!qq.isEmpty()) {
				int VEX=qq.poll();
				if(T.get(VEX).size()==0)continue;
				for(edge e:T.get(VEX)) {
					if(flag[e.to])continue;
					flag[e.to]=true;
					parents[e.to]=VEX;
					dist[e.to]=dist[VEX]+e.cost;
					qq.add(e.to);
				}
			}
		}
		void init_doubling() {
			//ダブリングによって、2^k先の祖先を前計算する。
			//doubling[T][i]=iから2^T個分先
			for (int i = 0; i < N; i++) {
				doubling[0][i]=parents[i];
			}
			for (int T = 1; T < doubling.length; T++) {
				for (int i = 0; i < N; i++) {
					doubling[T][i]=doubling[T-1][doubling[T-1][i]];
				}
			}
		}
		int get_doubling(int from,long K) {
			//ダブリングによって、fromからK先の祖先を求める。
			//longにするときは、doublingの長さも変えないとだから注意
			int res=from;
			for (int i = 0; i < doubling.length; i++) {
				if(((K>>i)&1)==0)continue;
				res=doubling[i][res];
			}
			return res;

		}
		int query(int u1,int v1) {
			//親からの距離を等しくする。(dist[u1]>dist[v1]とする)
			//System.out.println(u1+" "+v1+" "+get_doubling(u1, dist[u1]-dist[v1]));
			u1=get_doubling(u1, dist[u1]-dist[v1]);
			if(u1==v1)return v1;
			//二分探索によって、LCAの手前まで移動させる。

			int G=30;
			while(G>=0) {
				int uTO=doubling[G][u1];
				int vTO=doubling[G][v1];
				if(uTO!=vTO) {
					u1=uTO;
					v1=vTO;
				}
				G--;
			}
			//System.out.println(parents[u1]+" "+parents[v1]+" "+dist[u1]+" "+dist[v1]+" "+u1+" "+v1);
			return parents[u1];
		}
		int get_LCA(int u,int v) {
			if(!is_built)build();
			//根をrootとした時の、u,vのLCA(最小共通祖先)を返す。(0-indexed)
			if(dist[u]<dist[v]) {
				int temp=u;u=v;v=temp;
			}
			//dist[u]>dist[v]とする。
			return query(u,v);
		}
		long get_dist(int u,int v) {
			//u-vの距離
			if(!is_built)build();
			return -2*dist[get_LCA(u, v)]+dist[u]+dist[v];
		}
		boolean is_on_path(int u,int v,int a) {
			//u-vパス上に頂点aがあるか?
			//true:ある
			//false:ない
			return get_dist(u, a)+get_dist(a, v)==get_dist(u, v);
		}
		int INF=((1<<31)-1);
		long dmtr=-LINF;

		long get_diameter() {
			if(dmtr!=-INF)return dmtr;
			int V1=0;
			long max_dis=-LINF;
			for(int i=0;i<N;i++) {
				long d=get_dist(0, i);
				if(d>max_dis) {
					max_dis=d;
					V1=i;
				}
			}
			max_dis=-INF;
			//V1->V2への最大距離。
			for (int i = 0; i < N; i++) {
				max_dis=Math.max(max_dis,get_dist(V1,i));
			}
			return dmtr=max_dis;
		}
	}
	static class doubling{
		int N;
		int bits;
		int doubling[][];
		long COST[][];
		public doubling(int A[],int bits) {
			this.bits=bits;
			this.N=A.length;
			init1(A);
		}
		public doubling(int A[],int bits,long C[]) {
			// TODO 自動生成されたコンストラクター・スタブ
			//long C[]は、i=>A[i]に行くコスト
			//query2は、iからK番先までのコストの和で、i番までのコストが足されないので注意
			this.bits=bits;
			this.N=A.length;
			init1(A);
			init2(C);
		}
		private void init1(int A[]) {
			// TODO 自動生成されたメソッド・スタブ
			doubling=new int[bits][N];
			for (int i = 0; i < N; i++) {
				doubling[0][i]=A[i];
			}
			for (int t = 0; t+1 < bits; t++) {
				for (int i = 0; i < N; i++) {
					doubling[t+1][i]=doubling[t][doubling[t][i]];
				}
			}
		}
		private void init2(long C[]) {
			COST=new long[bits][N];
			for (int i = 0; i < N; i++) {
				COST[0][i]=C[i];//i番目からA[i]までのコスト
			}
			for (int t = 0; t+1 < bits; t++) {
				for (int i = 0; i < N; i++) {
					COST[t+1][i]=COST[t][doubling[t][i]]+COST[t][i];
				}
			}
		}
		//解釈
		private int query1(int start,long K) {
			//startからK回移動した後の座標を求める。
			int now=start;
			for (int i = 0; i < bits; i++) {
				if(((K>>i)&1)==1)now=doubling[i][now];
			}
			return now;
		}
		private long query2(int start,long K,long mod) {
			//STARTからK回移動した時のコストを計算する。
			int now=start;
			long res=0;
			for (int i = 0; i < bits; i++) {
				if(((K>>i)&1)==1) {
					res+=COST[i][now];
					now=doubling[i][now];
					res%=mod;
				}
			}
			return res;
		}
		private int query3(int start) {
			//startからスタートして、ループに入る時、そのループの長さを返す。



			return 1;
		}
	}
	static class DIKSTR{
		ArrayList<ArrayList<edge2>> m;
		ArrayList<ArrayList<edge2>> rev;
		static Map<String,Integer> hash=new HashMap<>();
		static int hash_count=0;
		long d[];
		int V,E;
		class edge2{
			int to;
			long cost;
			public edge2(int to,long cost) {
				this.to=to;
				this.cost=cost;
			}
		}
		class pair implements Comparable<pair>{
			int VEX;
			long cost;
			public pair(long cost,int VEX) {
				this.VEX=VEX;
				this.cost=cost;
			}
			public int compareTo(pair o) {
				Comparable key = (Comparable)this.cost;
				Comparable key2 = (Comparable)o.cost;
				return key.compareTo(key2);
			}
		}
		public DIKSTR(int V,int E) {
			this.V=V;//最大の頂点数。
			this.E=E;//最大の辺数。
			init();
		}
		public DIKSTR(int V) {
			this.V=V;
			this.E=0;
			init();
		}
		void init() {
			m=new ArrayList<>();
			rev=new ArrayList<>();
			for (int i = 0; i < V; i++) {
				m.add(new ArrayList<>());
				rev.add(new ArrayList<>());
			}
			d=new long[V];
		}
		void add_edge(int FROM,int TO,long COST) {
			m.get(FROM).add(new edge2(TO, COST));
			rev.get(TO).add(new edge2(FROM, COST));
		}
		void add_edge(String FROM,String TO,long COST) {
			if(!hash.containsKey(FROM))hash.put(FROM, hash_count++);
			if(!hash.containsKey(TO))hash.put(TO, hash_count++);
			add_edge(get_hash(FROM), get_hash(TO), COST);
		}
		int get_hash(String T) {
			if(!hash.containsKey(T)) {
				hash.put(T, hash_count++);
			}
			return hash.get(T);
		}
		long[] dikstr(String r) {
			return dikstr(get_hash(r));
		}
		long[] dikstr(int r) {//rは始点
			Arrays.fill(d, LINF);
			d[r]=0;
			PriorityQueue<pair> p = new PriorityQueue<>();//add poll
			p.add(new pair(0L, r));
			int count=0;
			while(!p.isEmpty()) {
				count++;
				if(count>V*V)break;
				pair x=p.poll();
				int from=x.VEX;
				if(x.cost>d[from])continue;
				for (int i = 0; i < m.get(from).size(); i++) {
					edge2 e=m.get(from).get(i);
					long COST=COST(e.cost);
					if(d[e.to]>d[from]+COST) {
						d[e.to]=d[from]+COST;
						p.add(new pair(d[e.to], e.to));
					}
				}
			}
			return d.clone();
		}
		ArrayList<Integer> back(int F,int to){
			//両方から辺が生えてる場合しか使えないよ！
			ArrayList<Integer> res=new ArrayList<>();
			Queue<Integer> q=new ArrayDeque<>(); //add,poll,peek BFSは前から実行される
			q.add(F);
			res.add(F);
			while(!q.isEmpty()) {
				count++;
				int from=q.poll();
				for (int i = 0; i < rev.get(from).size(); i++) {
					edge2 e=rev.get(from).get(i);
					long COST=COST(e.cost);
					if(e.to==from)continue;
					if(d[e.to]+COST==d[from]) {
						q.add(e.to);
						res.add(e.to);
						break;
					}
				}
			}
			
			return res;
			
		}
		long COST(long e_cost) {


			return e_cost;
		}
	}


	static Map<Integer, ArrayList<Integer>> getTree(int N){
		Map<Integer, ArrayList<Integer>> m = new HashMap<>();
		for (int i = 0; i < N; i++) {
			int a = sc.nextInt() - 1, b = sc.nextInt() - 1;
			if (!m.containsKey(a))
				m.put(a, new ArrayList<Integer>());
			if (!m.containsKey(b))
				m.put(b, new ArrayList<Integer>());
			m.get(a).add(b);
			m.get(b).add(a);
		}
		return m;
	}
	/*static Map<V,Integer> makeTree(Map<Integer, ArrayList<Integer>> m){
		//頂点0を根とした木を構築する。
		Queue<Integer> qq = new ArrayDeque<>(); //add,poll,peek BFSは前から実行される
		Queue<Integer> parent = new ArrayDeque<>(); //add,poll,peek BFSは前から実行される
		qq.add(0);
		Map<V, Integer> T = new HashMap<>();
		parent.add(-1);
		Queue<Integer> color = new ArrayDeque<>();
		color.add(-1);
		while (!qq.isEmpty()) {
			int from = qq.poll();
			int p = parent.poll();
			int c=color.poll();
			int X=1;
			for (int V : m.get(from)) {
				if (V == p)continue;
				if(X==c)X++;
				qq.add(V);
				parent.add(from);
				color.add(X);
				System.out.println(from +" "+V+" "+X);
				T.put(new V(from,V), X++);

			}
		}
		return T;
	}*/
	static boolean isHaveSameBit(int a,int b) {//同じbitを持っているか
		int t=0;
		while((a>>t)!=0) {
			if(((a>>t)&1)==1&&((b>>t)&1)==1)return true;
			t++;
		}
		return false;
	}
	static boolean isPalindrome(String S) {//回分になってるか
		for (int i = 0; i < S.length()/2; i++) {
			if(S.charAt(i)!=S.charAt(S.length()-i-1)) {
				return false;
			}
		}
		return true;
	}
	static int G[];
	static boolean xl100=false;
	static long modinv(long a,long mod) {
		if(!xl100) {
			G=new int[3000000];
			xl100=true;
		}
		int temp=(int)a;
		if(G[(int)a]!=0)return G[(int)a];
		
		long b=mod,u=1,v=0;
		while(b!=0) {
			long t=a/b;
			a-=t*b;long tem=a;a=b;b=tem;
			u-=t*v;tem=u;u=v;v=tem;
		}
		u%=mod;
		if(u<0)u+=mod;
		return G[temp]=(int)u;
	}
	static long[] extendedGCD(long a, long b) {
        long s = 0, old_s = 1;
        long t = 1, old_t = 0;
        long r = b, old_r = a;
        while(r != 0) {
            long q = old_r / r;
            long old_s0 = old_s, old_t0 = old_t, old_r0 = old_r;
            old_s = s;
            s = old_s0 - q * s;
            old_t = t;
            t = old_t0 - q * t;
            old_r = r;
            r = old_r0 - q * r;
        }
        return new long[] {old_s, old_t};
    }
	static class graph{
		public graph() {
			// TODO 自動生成されたコンストラクター・スタブ
		}
		//コンテスト中だけ
		static class edge3{
			int to;
			long cost;
			int K;
			public edge3(int to,long cost) {
				this.to=to;
				this.cost=cost;
			}
			public edge3(int to,long cost,int K) {
				this.to=to;
				this.cost=cost;
				this.K=K;
			}
		}
		long costV(long T,int K) {
			//T以上の最小のKの倍数を返す。
			long V=(T+K-1)/K;
			return V*K;
		}
		long[] adddikstr(int V,int E,int r,Map<Integer, ArrayList<edge3>> m) {
			d=new long[V];
			Arrays.fill(d, LINF);
			d[r]=0;
			PriorityQueue<Pair<Long,Integer>> p = new PriorityQueue<>();//add poll
			p.add(new Pair<Long, Integer>(0L, r));
			while(!p.isEmpty()) {
				Pair<Long,Integer> x=p.poll();
				int from=x.getValue();
				if(x.getKey()>d[from])continue;
				if(!m.containsKey(from))continue;
				for (int i = 0; i < m.get(from).size(); i++) {
					edge3 e=m.get(from).get(i);
					if(d[e.to]>costV(d[from],e.K)+e.cost) {
						d[e.to]=costV(d[from],e.K)+e.cost;
						p.add(new Pair<Long, Integer>(d[e.to], e.to));
					}
				}
			}
			return d;
		}
		//

		class edge implements Comparable<edge>{
			int from,to;
			long cost;
			public edge(int from,int to,long b) {
				this.from=from;
				this.to=to;
				this.cost=b;
			}
			@Override
			public int compareTo(edge o) {

				Comparable key = (Comparable)this.cost;
				Comparable key2 = (Comparable)o.cost;


				return key.compareTo(key2);
			}
		}
		static class edge2{
			int to;
			long cost;
			String FROM,TO;
			public edge2(int to,long cost) {
				this.to=to;
				this.cost=cost;
			}
			public edge2(int to,long cost,String FROM,String TO) {
				this.to=to;
				this.cost=cost;
				this.FROM=FROM;
				this.TO=TO;
			}
		}
		//単一始点最短距離問題(ダイクストラ法) 負閉路対策不可 経路復元
		long d[];
		long[] dikstr(int V,int E,int r,Map<Integer, ArrayList<edge2>> m) {
			//int path[]=new int[V];
			//Arrays.fill(path, -1);
			d=new long[V];
			Arrays.fill(d, LINF);
			d[r]=0;
			PriorityQueue<Pair<Long,Integer>> p = new PriorityQueue<>();//add poll
			p.add(new Pair<Long, Integer>(0L, r));
			while(!p.isEmpty()) {
				Pair<Long,Integer> x=p.poll();
				int from=x.getValue();
				if(x.getKey()>d[from])continue;
				if(!m.containsKey(from))continue;
				for (int i = 0; i < m.get(from).size(); i++) {
					edge2 e=m.get(from).get(i);
					if(d[e.to]>d[from]+e.cost) {
						d[e.to]=d[from]+e.cost;
						p.add(new Pair<Long, Integer>(d[e.to], e.to));
						//path[e.to]=from;
					}
				}
			}


			//経路復元
			//複数の経路を考える必要がある時は、pathに複数の同じような最短経路の辺を保存しておく
			//ArrayList<Integer> PATHs = new ArrayList<>();
			//int t=V-1;//goalから逆算する この場合0=goal
			//for(;t!=-1;t=path[t]) {
			//	PATHs.add(t);
			//}
			//p(path);
			//Collections.reverse(PATHs);
			//System.out.println(PATHs);

			return d.clone();
		}
		long[] additionalDikstr(int V,int E,int r,Map<Integer, ArrayList<edge2>> m,int banned) {
			//int path[]=new int[V];
			//Arrays.fill(path, -1);
			d=new long[V];
			Arrays.fill(d, LINF);
			d[r]=0;
			PriorityQueue<Pair<Long,Integer>> p = new PriorityQueue<>();//add poll
			p.add(new Pair<Long, Integer>(0L, r));
			while(!p.isEmpty()) {
				Pair<Long,Integer> x=p.poll();
				int from=x.getValue();
				if(x.getKey()>d[from])continue;
				if(!m.containsKey(from))continue;
				for (int i = 0; i < m.get(from).size(); i++) {
					edge2 e=m.get(from).get(i);
					if(from==banned&&e.to==0)continue;
					if(d[e.to]>d[from]+e.cost) {
						d[e.to]=d[from]+e.cost;
						p.add(new Pair<Long, Integer>(d[e.to], e.to));
					}
				}
			}
			return d.clone();
		}
		int D[];
		int[] Intdikstr(int V,int E,int r,Map<Integer, ArrayList<edge2>> m) {
			//int path[]=new int[V];
			//Arrays.fill(path, -1);
			D=new int[V];
			Arrays.fill(D, INF);
			D[r]=0;
			PriorityQueue<Pair<Integer,Integer>> p = new PriorityQueue<>();//add poll
			p.add(new Pair<Integer, Integer>(0, r));
			while(!p.isEmpty()) {
				Pair<Integer,Integer> x=p.poll();
				int from=x.getValue();
				if(x.getKey()>D[from])continue;
				if(!m.containsKey(from))continue;
				for (int i = 0; i < m.get(from).size(); i++) {
					edge2 e=m.get(from).get(i);
					if(D[e.to]>D[from]+e.cost) {
						D[e.to]=(int) (D[from]+e.cost);
						p.add(new Pair<Integer, Integer>(D[e.to], e.to));
						//path[e.to]=from;
					}
				}
			}
			p.clear();

			//経路復元
			//複数の経路を考える必要がある時は、pathに複数の同じような最短経路の辺を保存しておく
			//ArrayList<Integer> PATHs = new ArrayList<>();
			//int t=V-1;//goalから逆算する この場合0=goal
			//for(;t!=-1;t=path[t]) {
			//	PATHs.add(t);
			//}
			//p(path);
			//Collections.reverse(PATHs);
			//System.out.println(PATHs);

			return D;
		}
		//単一始点最短距離問題(ベルマンフォード法) 負閉路対策済み
		long[] Bellman_Ford(int V,int E,int r,edge e[]) {
			long d[]=new long[V]; //0~eのグラフはこれ

			//Map<Integer, Integer> d = new HashMap<>();それ以外はこれ
			//for(int i=0;i<E;i++) {
			//	if(!d.containsKey(e[i].to))m.add(new Pair<Integer, Integer>(e[i].to, INF));
			//	if(!d.containsKey(e[i].from))m.add(new Pair<Integer, Integer>(e[i].from, INF));
			//}

			//d.replace(r, 0);

			Arrays.fill(d, INF);
			d[r]=0;
			int count=0;
			while(true) {
				boolean update =false;
				for(int i=0;i<E;i++) {
					if(d[e[i].from]!=INF&&d[e[i].from]+e[i].cost<d[e[i].to]) {
						update=true;
						d[e[i].to]=d[e[i].from]+e[i].cost;
					}
				}
				if(!update)break;
				if(count==V) {
					p("NEGATIVE CYCLE");
					return null;
				}
				count++;
			}
			return d;
		}
		//最小全域木問題(クラスカル法)
		long kruskal(int V,edge es[]) {
			unionFind uf = new unionFind(V);
			Arrays.sort(es);
			long res=0;
			for (int i = 0; i < es.length; i++) {
				if(uf.find( es[i].from)!=uf.find( es[i].to)) {
					uf.union(es[i].from, es[i].to);
					res+=es[i].cost;
				}
			}
			return res;
		}
	}
	private static long Pow(long i,long t) {
		//iのt乗をO(log t)で返す
		long a=i;
		long res=1;
		//for(int i=0;i<S.length();i++) {if(S.charAt(N-i)=='1') {res=res*a%mod;}
		//tをStringで受け取りたい時用
		while(t!=0) {
			if((1&t)==1) {
				res=res*a;
			}
			a=a*a;
			t=t>>1;
		}
		return res;
	}
	private static Map<Long, Integer> primeNumbers(long N) {//素因数列挙
		Map<Long, Integer> c = new HashMap<>();
		for(long i=2;i*i<=N;i++) {
			if(N%i==0) {
				int count=0;
				while(N%i==0) {
					N/=i;
					count++;
				}
				c.put(i, count);
				continue;
			}
		}
		if(N!=1) {
			c.put(N, 1);
		}
		return c;
	}
//=========================Union Find=============================================
    //union idx2 tree to idx1 tree O(a(N))
static class unionFind{
	int UNI[],n,graph_s;
	int size[];
	public unionFind(int N) {
		// TODO 自動生成されたコンストラクター・スタブ
		n=N;
		graph_s=N;
		init();
	}
	void init() {
		UNI=new int[n];
		size=new int[n];
		Arrays.fill(size, 1);
		for (int i = 0; i < n; i++) {
			UNI[i]=i;
		}
	}
	int get(int idx) {
		return UNI[idx];
	}
	int find(int idx) {//木の根のindexを返す
        if(UNI[idx]==idx) return idx;
        return UNI[idx] = find(UNI[idx]);

    }
	 void shape() {//木の根に直接つなげる 経路圧縮
		for(int i=0;i<n;i++) {
			find(i);
		}
	}
    void union(int idx1,int idx2) {//idx1の根にidx2をつなげる
        int root1 = find(idx1);
        int root2 = find(idx2);
        if(UNI[root2]!=root1) {
        	graph_s--;
        	size[root1]+=size[root2];
            size[root2]=size[root1];
        }
        UNI[root2] = root1;
        
    }
    int get_size(int idx) {
    	return size[find(idx)];
    }
    void breaker(int idx1,int idx2) {
    	UNI[idx1]=idx1;
    }
   int MaxSize() {//最も大きい木の頂点数を返す
		shape();
		int V[]=new int[n];
		int max=0;
		for(int i=0;i<n;i++) {
			V[UNI[i]]++;
			max=Math.max(max, V[UNI[i]]);
		}
		return max;
	}
    int sum() {//木の数を返す
    	int res=0;
    	for(int i=0;i<n;i++) {
    		if(UNI[i]==i)res++;
    	}
    	return res;
    }
    void union2(int tree[],int idx1,int idx2) {//idx1の根にidx2をつなげる
        int root1 = find(idx1);
        int root2 = find(idx2);
        if(root1==root2)return;
        if(c.get(root1).size()>c.get(root2).size()) {
        	//root2をroot1に移し替える
        	for(int a:c.get(root2).keySet()) {
            	if(!c.get(root1).containsKey(a)) {
            		c.get(root1).put(a, 0);
            	}
            	c.get(root1).replace(a, c.get(root1).get(a)+c.get(root2).get(a));
            }
        	tree[root2] = root1;
        }else {
        	for(int a:c.get(root1).keySet()) {
            	if(!c.get(root2).containsKey(a)) {
            		c.get(root2).put(a, 0);
            	}
            	c.get(root2).replace(a, c.get(root2).get(a)+c.get(root1).get(a));
            }
        	tree[root1] = root2;
        }
    }
}

//=========================二分探索=============================================

private static class binarySearch{
	public binarySearch() {
		// TODO 自動生成されたコンストラクター・スタブ
	}
	int BinarySearch(int A[],int value) {
		int S=0,E=A.length,G=-1;
		while(S<=E) {
			G=(S+E)/2;
			if(A[G]==value)return G;
			else if(A[G]>value) {
				if(E==G)break;E=G;
			}else if(A[G]<value) {
				if(S==G)break;S=G;
			}
		}
		return -1;
	}
	int lowerBound(int A[],int value) {
		//A[i-1]<value<=A[i] value以上のindexを返す
		//value未満しかなかったらA.lengthを返す
		//value以上の個数は(A.length-i)value未満の個数はiである
		int S=0,E=A.length,G=-1;
		if(value<=A[0])return 0;
		if(A[A.length-1]<value)return A.length;

		while(true) {
			G=(S+E)/2;
			if(A[G]>=value&&A[G-1]<value) {
				return G;
			}else if(A[G]>=value) {
				E=G;
			}else if(A[G]<value) {
				S=G;
			}

		}
	}
	int upperBound(int A[],int value) {
		//A[i-1]<=value<A[i] valueより大きい数のindexの最小値を返す
		//value以下しかなかったらA.lengthを返す
		//valueより大きい数の個数は(A.length-i)value以下の個数はiである
		int S=0,E=A.length,G=-1;
		if(A[0]>value)return 0;
		if(A[A.length-1]<=value)return A.length;

		while(true) {
			G=(S+E)/2;
			if(A[G]>value&&A[G-1]<=value) {
				return G;
			}else if(A[G]>value) {
				E=G;
			}else if(A[G]<=value) {
				S=G;
			}

		}
	}
	int lowerBound(long A[],long value) {
		//A[i-1]<value<=A[i] value以上の最小indexを返す
		//value未満しかなかったらA.lengthを返す
		//value以上の個数は(A.length-i)value未満の個数はiである
		int S=0,E=A.length,G=-1;
		if(A[0]>=value)return 0;
		if(A[A.length-1]<value)return A.length;

		while(true) {
			G=(S+E)/2;
			if(A[G]>=value&&A[G-1]<value) {
				return G;
			}else if(A[G]>=value) {
				E=G;
			}else if(A[G]<value) {
				S=G;
			}
		}
	}
	int upperBound(long A[],long value) {
		//A[i-1]<=value<A[i] valueより大きい数のindexの最小値を返す
		//value以下しかなかったらA.lengthを返す
		//valueより大きい数の個数は(A.length-i)value以下の個数はiである
		int S=0,E=A.length,G=-1;
		if(A[0]>value)return 0;
		if(A[A.length-1]<=value)return A.length;

		while(true) {
			G=(S+E)/2;

			if(A[G]>value&&A[G-1]<=value) {
				return G;
			}else if(A[G]>value) {
				E=G;
			}else if(A[G]<=value) {
				S=G;
			}

		}
	}

	int lowerBound(ArrayList<Integer> A,int value) {
		//A[i-1]<value<=A[i] value以上のindexを返す
		//value未満しかなかったらA.lengthを返す
		//value以上の個数は(A.length-i)value未満の個数はiである
		int S=0,E=A.size(),G=-1;
		if(A.get(0)>=value)return 0;
		if(A.get(A.size()-1)<value)return A.size();

		while(true) {
			G=(S+E)/2;

			if(A.get(G)>=value&&A.get(G-1)<value) {
				return G;
			}else if(A.get(G)>=value) {
				E=G;
			}else if(A.get(G)<value) {
				S=G;
			}

		}
	}
	int upperBound(ArrayList<Integer> A,int value) {
		//A[i-1]<=value<A[i] valueより大きい数のindexの最小値を返す
		//value以下しかなかったらA.lengthを返す
		//valueより大きい数の個数は(A.length-i)value以下の個数はiである
		int S=0,E=A.size(),G=-1;
		if(A.get(0)>value)return 0;
		if(A.get(A.size()-1)<=value)return A.size();

		while(true) {
			G=(S+E)/2;

			if(A.get(G)>value&&A.get(G-1)<=value) {
				return G;
			}else if(A.get(G)>value) {
				E=G;
			}else if(A.get(G)<=value) {
				S=G;
			}

		}
	}
	int lowerBound(ArrayList<Long> A,long value) {
		//A[i-1]<value<=A[i] value以上のindexを返す
		//value未満しかなかったらA.lengthを返す
		//value以上の個数は(A.length-i)value未満の個数はiである
		int S=0,E=A.size(),G=-1;
		if(A.get(0)>=value)return 0;
		if(A.get(A.size()-1)<value)return A.size();

		while(true) {
			G=(S+E)/2;

			if(A.get(G)>=value&&A.get(G-1)<value) {
				return G;
			}else if(A.get(G)>=value) {
				E=G;
			}else if(A.get(G)<value) {
				S=G;
			}

		}
	}
	int upperBound(ArrayList<Long> A,long value) {
		//A[i-1]<=value<A[i] valueより大きい数のindexの最小値を返す
		//value以下しかなかったらA.lengthを返す
		//valueより大きい数の個数は(A.length-i)value以下の個数はiである
		int S=0,E=A.size(),G=-1;
		if(A.get(0)>value)return 0;
		if(A.get(A.size()-1)<=value)return A.size();

		while(true) {
			G=(S+E)/2;

			if(A.get(G)>value&&A.get(G-1)<=value) {
				return G;
			}else if(A.get(G)>value) {
				E=G;
			}else if(A.get(G)<=value) {
				S=G;
			}

		}
	}
}
	private static long modNcR2(int n,int r,int mod) {
		if(r<0||n<r)return 0;
		long N=kaijou(n, mod);
		long Nr=kaijou(n-r, mod);
		long R=kaijou(r, mod);
		return (((N*modPow(Nr, mod-2, mod))%mod)*modPow(R, mod-2, mod))%mod;
		// n!/(n-r)!/r!


	}
	private static long modNcR(int n,int r,long mod) {
		if(r<0||n<r)return 0;
		long res=1;
		for(int i=0;i<r;i++) {
			res*=n;res%=mod;n--;
		}
		for(int i=0;i<r;i++) {
			res*=modinv(i+1, mod);res%=mod;
			
		}
		return res;
		// n!/(n-r)!/r!


	}
	
	private static long modNcR2(int n,int r,long mod) {
		if(r<0||n<r)return 0;
		long N=kaijou(n, mod);
		long Nr=kaijou(n-r, mod);
		long R=kaijou(r, mod);
		return (((N*modPow(Nr, mod-2, mod))%mod)*modPow(R, mod-2, mod))%mod;
		// n!/(n-r)!/r!


	}
	private static long modPow(long i,long t,long mod) {
		if(t==0)return 1%mod;
		if(i==0||t<0)return 0;//0未満乗は未定義で
		//iのt乗をO(log t)で返す
		i%=mod;
		long a=i;
		long res=1;
		//for(int i=0;i<S.length();i++) {if(S.charAt(N-i)=='1') {res=res*a%mod;}
		//tをbitのStringで受け取った時用？
		while(t!=0) {
			if((1&t)==1) {
				res=res*a%mod;
			}
			a=a*a%mod;
			t=t>>1;
		}
		return res;
	}
	private static long min(long ...a) {
		long m=a[0];
		for(int i=0;i<a.length;i++) {
			m=Math.min(a[i], m);
		}
		return m;
	}
	private static int min(int ...a) {
	int m=a[0];
	for(int i=0;i<a.length;i++) {
		m=Math.min(a[i], m);
	}
	return m;
}
	private static int max(int ...a) {
	int m=a[0];
	for(int i=0;i<a.length;i++) {
		m=Math.max(a[i], m);
	}
	return m;
}
	private static long max(long ...a) {
		long m=a[0];
		for(int i=0;i<a.length;i++) {
			m=Math.max(a[i], m);
		}
		return m;
	}
	private static double min(double ...a) {
		double m=a[0];
	for(int i=0;i<a.length;i++) {
		m=Math.min(a[i], m);
	}
	return m;
}
	private static double max(double ...a) {
	double m=a[0];
	for(int i=0;i<a.length;i++) {
		m=Math.max(a[i], m);
	}
	return m;
}
	private static int abs(int a) {
		return Math.max(a,-a);
	}
	private static long abs(long a) {
		return Math.max(a,-a);
	}
	private static double abs(double a) {
		return Math.max(a,-a);
	}
	private static String zeroume(String S,int V) {
		while(S.length()<V)S='0'+S;
		return S;
	}

	//速度が足りないときは、前計算を1回だけにしたり、longをintに変えたりするといい
	//エラストネスの篩風のやつもあり
	private static long gcd(long ...nums) {
		long res=0;
		for (int i = 0; i < nums.length; i++) {
			res=gcd(res,nums[i]);
		}
		return res;
	}
	private static long lcm(long ...nums) {
		long res=1;
		for (int i = 0; i < nums.length; i++) {
			res=lcm(res,nums[i]);
		}
		return res;
	}
	public static long gcd(long num1,long num2) {
        if(num2==0) return num1;
        else return gcd(num2,num1%num2);
    }
	public static long lcm(long num1,long num2) {
		return num1*num2/gcd(num1,num2);
	}
	//O(N^0.5)
		private static void bubunwa() {
		int N=sc.nextInt();
		int K=sc.nextInt();
		int a[]=sc.nextIntArray(N, false);

		boolean dp[] =new boolean[K+1];

		Arrays.fill(dp, false);
		dp[0]=true;
		for(int i=0;i<N;i++) {
			for(int x=K-a[i];x>=0;x--) {
				if(dp[x])dp[x+a[i]]=true;
			}
		}

		p(dp[K] ? "Yes":"No");
	}


		static String nextPermutation(String s) {
		ArrayList<Character> list=new ArrayList<Character>();
		for(int i=0;i<s.length();i++) {
			list.add(s.charAt(i));
		}
		int pivotPos=-1;
		char pivot=0;
		for(int i=list.size()-2;i>=0;i--) {
			if(list.get(i)<list.get(i+1)) {
				pivotPos=i;
				pivot=list.get(i);
				break;
			}
		}
		if(pivotPos==-1&&pivot==0) {
			return "Final";
		}
		int L=pivotPos+1,R=list.size()-1;		int minPos=-1;
		char min =Character.MAX_VALUE;

		for(int i=R;i>=L;i--) {
			if(pivot<list.get(i)) {
				if(list.get(i)<min) {
					min=list.get(i);
					minPos=i;
				}
			}
		}

		Collections.swap(list, pivotPos, minPos);
		Collections.sort(list.subList(L, R+1));
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<list.size();i++) {
			sb.append(list.get(i));
		}
		return sb.toString();
	}
	private static long[][] com;
	private static void nCr(int mod) {
	int MAX = 3001;
	com= new long[MAX][MAX];
	for(int i = 0; i < MAX; i++)
	    com[i][0] = 1;
	for(int i = 1; i < MAX; i++) {
	    for(int j = 1; j <= i; j++) {
	        com[i][j] = com[i-1][j-1] + com[i-1][j];
	        com[i][j] %= mod;
	    }
	}
}
	//https://qiita.com/p_shiki37/items/65c18f88f4d24b2c528b より
	static class FastScanner {
	    private final InputStream in = System.in;
	    private final byte[] buffer = new byte[1024];
	    private int ptr = 0;
	    private int buflen = 0;

	    private boolean hasNextByte() {
	      if (ptr < buflen) {
	        return true;
	      } else {
	        ptr = 0;
	        try {
	          buflen = in.read(buffer);
	        } catch (IOException e) {
	          e.printStackTrace();
	        }
	        if (buflen <= 0) {
	          return false;
	        }
	      }
	      return true;
	    }

	    private int readByte() {
	      if (hasNextByte()) return buffer[ptr++];
	      else return -1;
	    }

	    private static boolean isPrintableChar(int c) {
	      return 33 <= c && c <= 126;
	    }

	    private void skipUnprintable() {
	      while (hasNextByte() && !isPrintableChar(buffer[ptr])) ptr++;
	    }

	    public boolean hasNext() {
	      skipUnprintable();
	      return hasNextByte();
	    }

	    public String next() {
	      if (!hasNext()) throw new NoSuchElementException();
	      StringBuilder sb = new StringBuilder();
	      int b = readByte();
	      while (isPrintableChar(b)) {
	        sb.appendCodePoint(b);
	        b = readByte();
	      }
	      return sb.toString();
	    }

	    public long nextLong() {
	      if (!hasNext()) throw new NoSuchElementException();
	      long n = 0;
	      boolean minus = false;
	      int b = readByte();
	      if (b == '-') {
	        minus = true;
	        b = readByte();
	      }
	      if (b < '0' || '9' < b) {
	        throw new NumberFormatException();
	      }
	      while (true) {
	        if ('0' <= b && b <= '9') {
	          n *= 10;
	          n += b - '0';
	        } else if (b == -1 || !isPrintableChar(b)) {
	          return minus ? -n : n;
	        } else {
	          throw new NumberFormatException();
	        }
	        b = readByte();
	      }
	    }

	    public int nextInt() {
	      return (int) nextLong();
	    }

	    public double nextDouble(){
	    	return Double.parseDouble(next());
	    }
	    public int[] nextIntArray(int N) {

		        int[] array = new int[N];
		        for (int i = 0; i < N; i++) {
		          array[i] = sc.nextInt();
		        }
		        return array;

		    }
	    public int[] nextIntArray(int N, boolean oneBased) {
	      if (oneBased) {
	        int[] array = new int[N + 1];
	        for (int i = 1; i <= N; i++) {
	          array[i] = sc.nextInt();
	        }
	        return array;
	      } else {
	        int[] array = new int[N];
	        for (int i = 0; i < N; i++) {
	          array[i] = sc.nextInt();
	        }
	        return array;
	      }
	    }

	    public long[] nextLongArray(int N, boolean oneBased) {
	      if (oneBased) {
	        long[] array = new long[N + 1];
	        for (int i = 1; i <= N; i++) {
	          array[i] = sc.nextLong();
	        }
	        return array;
	      } else {
	        long[] array = new long[N];
	        for (int i = 0; i < N; i++) {
	          array[i] = sc.nextLong();
	        }
	        return array;
	      }
	    }
	    public int[] nextRandIntArray(int N, boolean oneBased,int max) {
		    Random r=new Random();
		    if(oneBased)N++;
		    int array[]=new int[N];
	    	for(int i=0;i<N;i++) {
	    		array[i]=r.nextInt(max+1);
	    	}
	    	if(oneBased)array[0]=0;
	    	return array;
		    }
	    public long[] nextLongArray(int N) {
		        long[] array = new long[N];
		        for (int i = 0; i < N; i++) {
		          array[i] = sc.nextLong();
		        }
		        return array;
		      }
	    public long[][]nextLongDimensionalArray(int H,int W) {
	        long[][] array = new long[H][W];
	        for (int i = 0; i < H; i++) {
	          array[i] =sc.nextLongArray(W);
	        }
	        return array;
			}
			public int[][]nextIntDimensionalArray(int H,int W) {
				int[][] array = new int[H][W];
		        for (int i = 0; i < H; i++) {
		          array[i] =sc.nextIntArray(W);
		        }
		        return array;
				}

			public String[] nextArray(int N) {
				String[] array = new String[N];
		        for (int i = 0; i < N; i++) {
		          array[i] = sc.next();
		        }
		        return array;
		      }
			public String[][]nextDimensionalArray(int H,int W) {
				String[][] array = new String[H][W];
		        for (int i = 0; i < H; i++) {
		          array[i] =sc.nextArray(W);
		        }
		        return array;
				}
			public double[] nextDoubleArray(int N) {
				double[] array = new double[N];
		        for (int i = 0; i < N; i++) {
		          array[i] = sc.nextDouble();
		        }
		        return array;
		      }
	  }


}


