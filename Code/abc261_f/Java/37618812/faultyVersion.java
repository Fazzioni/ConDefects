import java.io.*;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.*;


public class Main {
	
	public static void main(String[] args) throws IOException {
		new Thread(new Task()).start();
	}

	static class Task implements Runnable {
		
		public void run() {
			InputStream inputStream = System.in;
			OutputStream outputStream = System.out;
			PrintWriter out = new PrintWriter(outputStream);
			InputReader in = new InputReader(inputStream);

//			for(int i=4;i<=4;i++) {
//				InputStream uinputStream = new FileInputStream("timeline.in");
			// String f = i+".in";
			// InputStream uinputStream = new FileInputStream(f);
//				InputReader in = new InputReader(uinputStream);
//				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("timeline.out")));
//			}
			// PrintWriter out = new PrintWriter(new BufferedWriter(new
			// FileWriter("timeline.out")));

			try {
				solve(in, out);
			} catch (IOException e) {
				e.printStackTrace();
			}
			out.close();
			
		}	
		
		public void solve1(InputReader in, PrintWriter out) throws IOException {
			int n = in.nextInt();
			int m = in.nextInt();
			int arr[] = new int[n];
			int brr[] = new int[n];
			for(int i=0;i<n;i++) arr[i] = in.nextInt();
			for(int i=0;i<n;i++) brr[i] = in.nextInt();
			int crr[][] = new int[n][2];
			for(int i=0;i<n;i++) {
				crr[i][0] = Math.max(arr[i]-m, brr[i]-m);
				crr[i][1] = Math.min(arr[i]+m, brr[i]+m);
			}
			ArrayList<pair>[] g = new ArrayList[n];
			for(int i=0;i<n;i++) {
				g[i] = new ArrayList<pair>();
				for(int j=crr[i][0];j<=crr[i][1];j++) {
					int d1 = Math.abs(j-arr[i]);
					int d2 = Math.abs(j-brr[i]);
					g[i].add(new pair(d1,d2));
				}
			}
			int dp[][][] = new int[n+1][m+1][m+1];
			int f = 998244353;
			dp[0][0][0] = 1;
			for(int i=1;i<=n;i++) {
				for(pair t:g[i-1]) {
					for(int j=t.a;j<=m;j++) {
						for(int k=t.b;k<=m;k++) {
							dp[i][j][k] += dp[i-1][j-t.a][k-t.b];
							dp[i][j][k]%=f;
						}
					}
				}
			}
			int s = 0;
			for(int i=0;i<=m;i++) {
				for(int j=0;j<=m;j++) {
					s+=dp[n][i][j];
					s%=f;
				}
			}
			out.println(s);
		}
		class pair {
			int a; int b;
			public pair(int x,int y) {
				a=x;b=y;
			}
		}
    
	    
	    

		class edge1 implements Comparable<edge1>{
			int f,t,len;
			public edge1(int a, int b, int c) {
				f=a;t=b;len=c;
			}
			@Override
			public int compareTo(edge1 o) {
				// TODO Auto-generated method stub
				return 0;
			}		
		}

		public void solve(InputReader in, PrintWriter out) throws IOException {			
			int n = in.nextInt();
			int crr[] = in.readIntArray(n);
			int xrr[] = in.readIntArray(n);
			
			ArrayList<Integer>[] g = new ArrayList[n+1];
			for(int i=0;i<=n;i++) g[i] = new ArrayList<Integer>();
			for(int i=0;i<n;i++) {
				g[crr[i]].add(xrr[i]);
			}
			
			int ret = 0;
			BIT b = new BIT(n+1);
			for(int i=n-1;i>=0;i--) {
				ret+=b.sum(xrr[i]-1);//inversion  - same color
				b.add(xrr[i], 1);
			}
			
			b = new BIT(n+1);
			for(int i=1;i<=n;i++) {			
				for(int j=g[i].size()-1;j>=0;j--) {
					ret-=b.sum(g[i].get(j)-1);
					b.add(g[i].get(j), 1);
				}
				for(int j=g[i].size()-1;j>=0;j--) {
					b.add(g[i].get(j), -1);
				}
			}
			out.println(ret);
		}
		


		public class edge implements Comparable<edge> {
			int f, t;
			int len;
			int id;

			public edge(int a, int b, int c, int d) {
				f = a;
				t = b;
				len = c;
				id = d;
			}

			@Override
			public int compareTo(Main.Task.edge o) {
				if (this.len - o.len < 0)
					return -1;
				else if (this.len == o.len)
					return 0;
				else
					return 1;
			}
		}

		public Set<Integer> get_factor(int number) {
			int n = number;
			Set<Integer> primeFactors = new HashSet<Integer>();
			for (int i = 2; i <= n / i; i++) {
				while (n % i == 0) {
					primeFactors.add(i);
					n /= i;
				}
			}
			if (n > 1)
				primeFactors.add(n);
			return primeFactors;
		}

		private static long cnr(int n, int m, long mod, long fac[], long inv[]) {
			if (n < m)
				return 0;
			return fac[n] * inv[n - m] % mod * inv[m] % mod;
		}

		private static int combx(int n, int k, int mod) {
			if (n < k)
				return 0;
			int comb[][] = new int[n + 1][n + 1];
			for (int i = 0; i <= n; i++) {
				comb[i][0] = comb[i][i] = 1;
				for (int j = 1; j < i; j++) {
					comb[i][j] = comb[i - 1][j] + comb[i - 1][j - 1];
					comb[i][j] %= mod;
				}
			}
			return comb[n][k];
		}

		private static long qpow(long a, long p, long MOD) {
			long m = Long.highestOneBit(p);
			long ans = 1;
			for (; m > 0; m >>>= 1) {
				ans = ans * ans % MOD;
				if ((p & m) > 0)
					ans = ans * a % MOD;
			}
			return (int) ans;
		}

		static class lca_naive {
			int n;
			ArrayList<edge>[] g;
			int lvl[];
			int pare[];
			int dist[];

			public lca_naive(int t, ArrayList<edge>[] x) {
				n = t;
				g = x;
				lvl = new int[n];
				pare = new int[n];
				dist = new int[n];
			}

			void pre_process() {
				dfs(0, -1, g, lvl, pare, dist);
			}

			void dfs(int cur, int pre, ArrayList<edge>[] g, int lvl[], int pare[], int dist[]) {
				for (edge nxt_edge : g[cur]) {
					if (nxt_edge.t != pre) {
						lvl[nxt_edge.t] = lvl[cur] + 1;
						dist[nxt_edge.t] = (int) (dist[cur] + nxt_edge.len);
						pare[nxt_edge.t] = cur;
						dfs(nxt_edge.t, cur, g, lvl, pare, dist);
					}
				}
			}

			public int work(int p, int q) {
				int a = p;
				int b = q;
				while (lvl[p] < lvl[q])
					q = pare[q];
				while (lvl[p] > lvl[q])
					p = pare[p];
				while (p != q) {
					p = pare[p];
					q = pare[q];
				}
				int c = p;
				return dist[a] + dist[b] - dist[c] * 2;
			}
		}

		static class lca_binary_lifting {
			int n;
			ArrayList<edge>[] g;
			int lvl[];
			int pare[];
			int dist[];
			int table[][];

			public lca_binary_lifting(int a, ArrayList<edge>[] t) {
				n = a;
				g = t;
				lvl = new int[n];
				pare = new int[n];
				dist = new int[n];
				table = new int[20][n];
			}

			void pre_process() {
				dfs(0, -1, g, lvl, pare, dist);
				for (int i = 0; i < 20; i++) {
					for (int j = 0; j < n; j++) {
						if (i == 0)
							table[0][j] = pare[j];
						else
							table[i][j] = table[i - 1][table[i - 1][j]];
					}
				}
			}

			void dfs(int cur, int pre, ArrayList<edge>[] g, int lvl[], int pare[], int dist[]) {
				for (edge nxt_edge : g[cur]) {
					if (nxt_edge.t != pre) {
						lvl[nxt_edge.t] = lvl[cur] + 1;
						dist[nxt_edge.t] = (int) (dist[cur] + nxt_edge.len);
						pare[nxt_edge.t] = cur;
						dfs(nxt_edge.t, cur, g, lvl, pare, dist);
					}
				}
			}

			public int work(int p, int q) {
				int a = p;
				int b = q;
				if (lvl[p] > lvl[q]) {
					int tmp = p;
					p = q;
					q = tmp;
				}
				for (int i = 19; i >= 0; i--) {
					if (lvl[table[i][q]] >= lvl[p])
						q = table[i][q];
				}
				if (p == q)
					return p;// return dist[a]+dist[b]-dist[p]*2;
				for (int i = 19; i >= 0; i--) {
					if (table[i][p] != table[i][q]) {
						p = table[i][p];
						q = table[i][q];
					}
				}
				return table[0][p];
				// return dist[a]+dist[b]-dist[table[0][p]]*2;
			}
		}

		static class lca_sqrt_root {
			int n;
			ArrayList<edge>[] g;
			int lvl[];
			int pare[];
			int dist[];
			int jump[];
			int sz;

			public lca_sqrt_root(int a, ArrayList<edge>[] b) {
				n = a;
				g = b;
				lvl = new int[n];
				pare = new int[n];
				dist = new int[n];
				jump = new int[n];
				sz = (int) Math.sqrt(n);
			}

			void pre_process() {
				dfs(0, -1, g, lvl, pare, dist, jump);
			}

			void dfs(int cur, int pre, ArrayList<edge>[] g, int lvl[], int pare[], int dist[], int[] jump) {
				for (edge nxt_edge : g[cur]) {
					if (nxt_edge.t != pre) {
						lvl[nxt_edge.t] = lvl[cur] + 1;
						dist[nxt_edge.t] = (int) (dist[cur] + nxt_edge.len);
						pare[nxt_edge.t] = cur;
						if (lvl[nxt_edge.t] % sz == 0) {
							jump[nxt_edge.t] = cur;
						} else {
							jump[nxt_edge.t] = jump[cur];
						}
						dfs(nxt_edge.t, cur, g, lvl, pare, dist, jump);
					}
				}
			}

			int work(int p, int q) {
				int a = p;
				int b = q;
				if (lvl[p] > lvl[q]) {
					int tmp = p;
					p = q;
					q = tmp;
				}
				while (jump[p] != jump[q]) {
					if (lvl[p] > lvl[q])
						p = jump[p];
					else
						q = jump[q];
				}
				while (p != q) {
					if (lvl[p] > lvl[q])
						p = pare[p];
					else
						q = pare[q];
				}
				return dist[a] + dist[b] - dist[p] * 2;
			}
		}

		static class Combination {
		    private static final int MEMO_THRESHOLD = 1000000;
		    static long mod = 1000000007;
		    private static final List<Long> inv = new ArrayList<>();
		    private static final List<Long> fact = new ArrayList<>();
		    private static final List<Long> invFact = new ArrayList<>();
		    private static final Map<Long, List<Long>> pow = new HashMap<>();
		 
		    private static void buildInvTable(int n) {
		        if (inv.isEmpty()) {
		            inv.add(null);
		            inv.add(1L);
		        }
		        for (int i = inv.size(); i <= n; i++) {
		            inv.add(mod - inv.get((int)(mod % i)) * (mod / i) % mod);
		        }
		    }
		 
		    private static void buildFactTable(int n) {
		        if (fact.isEmpty()) {
		            fact.add(1L);
		            invFact.add(1L);
		        }
		        for (int i = fact.size(); i <= n; i++) {
		            fact.add(fact.get(i - 1) * i % mod);
		            invFact.add(inv(fact.get(i)));
		        }
		    }
		 
		    public static void setupPowTable(long a) {
		        pow.put(a, new ArrayList<>(Collections.singleton(1L)));
		    }
		 
		    private static void rangeCheck(long n, long r) {
		        if (n < r) {
		            throw new IllegalArgumentException("n < r");
		        }
		        if (n < 0) {
		            throw new IllegalArgumentException("n < 0");
		        }
		        if (r < 0) {
		            throw new IllegalArgumentException("r < 0");
		        }
		    }
		 
		    static long fact(int n) {
		        buildFactTable(n);
		        return fact.get(n);
		    }
		 
		    static long invFact(int n) {
		        buildFactTable(n);
		        return invFact.get(n);
		    }
		 
		    private static long comb0(int n, int r) {
		        rangeCheck(n, r);
		        return fact(n) * invFact(r) % mod * invFact(n - r) % mod;
		    }
		 
		    static long comb(long n, long r) {
		        rangeCheck(n, r);
		        if (n < MEMO_THRESHOLD) {
		            return comb0((int)n, (int)r);
		        }
		        r = Math.min(r, n - r);
		        long x = 1, y = 1;
		        for (long i = 1; i <= r; i++) {
		            x = x * (n - r + i) % mod;
		            y = y * i % mod;
		        }
		        return x * inv(y) % mod;
		    }
		 
		    private static long perm0(int n, int r) {
		        rangeCheck(n, r);
		        return fact(n) * invFact(n - r) % mod;
		    }
		 
		    static long perm(long n, long r) {
		        rangeCheck(n, r);
		        if (n < MEMO_THRESHOLD) {
		            return perm0((int)n, (int)r);
		        }
		        long x = 1;
		        for (long i = 1; i <= r; i++) {
		            x = x * (n - r + i) % mod;
		        }
		        return x;
		    }
		 
		    static long homo(long n, long r) {
		        return r == 0 ? 1 : comb(n + r - 1, r);
		    }
		 
		    private static long inv0(int a) {
		        buildInvTable(a);
		        return inv.get(a);
		    }
		 
		    static long inv(long a) {
		        if (a < MEMO_THRESHOLD) {
		            return inv0((int)a);
		        }
		        long b = mod;
		        long u = 1, v = 0;
		        while (b >= 1) {
		            long t = a / b;
		            a -= t * b;
		            u -= t * v;
		            if (a < 1) {
		                return (v %= mod) < 0 ? v + mod : v;
		            }
		            t = b / a;
		            b -= t * a;
		            v -= t * u;
		        }
		        return (u %= mod) < 0 ? u + mod : u;
		    }
		 
		    static long pow(long a, long b) {
		        if (pow.containsKey(a) && b < MEMO_THRESHOLD) {
		            return powMemo(a, (int)b);
		        }
		        long x = 1;
		        while (b > 0) {
		            if (b % 2 == 1) {
		                x = x * a % mod;
		            }
		            a = a * a % mod;
		            b >>= 1;
		        }
		        return x;
		    }
		 
		    static long powMemo(long a, int b) {
		        List<Long> powMemo = pow.get(a);
		        while (powMemo.size() <= b) {
		            powMemo.add(powMemo.get(powMemo.size() - 1) * a % Combination.mod);
		        }
		        return powMemo.get(b);
		    }
		}

		static class lca_RMQ {
			int n;
			ArrayList<edge>[] g;
			int lvl[];
			int dist[];
			int tour[];
			int tour_rank[];
			int first_occ[];
			int c;
			sgt s;

			public lca_RMQ(int a, ArrayList<edge>[] b) {
				n = a;
				g = b;
				c = 0;
				lvl = new int[n];
				dist = new int[n];
				tour = new int[2 * n];
				tour_rank = new int[2 * n];
				first_occ = new int[n];
				Arrays.fill(first_occ, -1);
			}

			void pre_process() {
				tour[c++] = 0;
				dfs(0, -1);
				for (int i = 0; i < 2 * n; i++) {
					tour_rank[i] = lvl[tour[i]];
					if (first_occ[tour[i]] == -1)
						first_occ[tour[i]] = i;
				}
				s = new sgt(0, 2 * n, tour_rank);
			}

			void dfs(int cur, int pre) {
				for (edge nxt_edge : g[cur]) {
					if (nxt_edge.t != pre) {
						lvl[nxt_edge.t] = lvl[cur] + 1;
						dist[nxt_edge.t] = (int) (dist[cur] + nxt_edge.len);
						tour[c++] = nxt_edge.t;
						dfs(nxt_edge.t, cur);
						tour[c++] = cur;
					}
				}
			}

			int work(int p, int q) {
				int a = Math.max(first_occ[p], first_occ[q]);
				int b = Math.min(first_occ[p], first_occ[q]);
				int idx = s.query_min_idx(b, a + 1);
				// Dumper.print(a+" "+b+" "+idx);
				int c = tour[idx];
				return dist[p] + dist[q] - dist[c] * 2;
			}
		}

		static class sgt {
			sgt lt;
			sgt rt;
			int l, r;
			int sum, max, min, lazy;
			int min_idx;

			public sgt(int L, int R, int arr[]) {
				l = L;
				r = R;
				if (l == r - 1) {
					sum = max = min = arr[l];
					lazy = 0;
					min_idx = l;
					return;
				}
				lt = new sgt(l, l + r >> 1, arr);
				rt = new sgt(l + r >> 1, r, arr);
				pop_up();
			}

			void pop_up() {
				this.sum = lt.sum + rt.sum;
				this.max = Math.max(lt.max, rt.max);
				this.min = Math.min(lt.min, rt.min);
				if (lt.min < rt.min)
					this.min_idx = lt.min_idx;
				else if (lt.min > rt.min)
					this.min_idx = rt.min_idx;
				else
					this.min = Math.min(lt.min_idx, rt.min_idx);
			}

			void push_down() {
				if (this.lazy != 0) {
					lt.sum += lazy;
					rt.sum += lazy;
					lt.max += lazy;
					lt.min += lazy;
					rt.max += lazy;
					rt.min += lazy;
					lt.lazy += this.lazy;
					rt.lazy += this.lazy;
					this.lazy = 0;
				}
			}

			void change(int L, int R, int v) {
				if (R <= l || r <= L)
					return;
				if (L <= l && r <= R) {
					this.max += v;
					this.min += v;
					this.sum += v * (r - l);
					this.lazy += v;
					return;
				}
				push_down();
				lt.change(L, R, v);
				rt.change(L, R, v);
				pop_up();
			}

			int query_max(int L, int R) {
				if (L <= l && r <= R)
					return this.max;
				if (r <= L || R <= l)
					return Integer.MIN_VALUE;
				push_down();
				return Math.max(lt.query_max(L, R), rt.query_max(L, R));
			}

			int query_min(int L, int R) {
				if (L <= l && r <= R)
					return this.min;
				if (r <= L || R <= l)
					return Integer.MAX_VALUE;
				push_down();
				return Math.min(lt.query_min(L, R), rt.query_min(L, R));
			}

			int query_sum(int L, int R) {
				if (L <= l && r <= R)
					return this.sum;
				if (r <= L || R <= l)
					return 0;
				push_down();
				return lt.query_sum(L, R) + rt.query_sum(L, R);
			}

			int query_min_idx(int L, int R) {
				if (L <= l && r <= R)
					return this.min_idx;
				if (r <= L || R <= l)
					return Integer.MAX_VALUE;
				int a = lt.query_min_idx(L, R);
				int b = rt.query_min_idx(L, R);
				int aa = lt.query_min(L, R);
				int bb = rt.query_min(L, R);
				if (aa < bb)
					return a;
				else if (aa > bb)
					return b;
				return Math.min(a, b);
			}
		}

		List<List<Integer>> convert(int arr[][]) {
			int n = arr.length;
			List<List<Integer>> ret = new ArrayList<>();
			for (int i = 0; i < n; i++) {
				ArrayList<Integer> tmp = new ArrayList<Integer>();
				for (int j = 0; j < arr[i].length; j++)
					tmp.add(arr[i][j]);
				ret.add(tmp);
			}
			return ret;
		}

		public class TreeNode {
			int val;
			TreeNode left;
			TreeNode right;

			TreeNode(int x) {
				val = x;
			}
		}

		public int GCD(int a, int b) {
			if (b == 0)
				return a;
			return GCD(b, a % b);
		}

		public long GCD(long a, long b) {
			if (b == 0)
				return a;
			return GCD(b, a % b);
		}
	}

	static class ArrayUtils {
		static final long seed = System.nanoTime();
		static final Random rand = new Random(seed);

		public static void sort(int[] a) {
			shuffle(a);
			Arrays.sort(a);
		}

		public static void shuffle(int[] a) {
			for (int i = 0; i < a.length; i++) {
				int j = rand.nextInt(i + 1);
				int t = a[i];
				a[i] = a[j];
				a[j] = t;
			}
		}

		public static void sort(long[] a) {
			shuffle(a);
			Arrays.sort(a);
		}

		public static void shuffle(long[] a) {
			for (int i = 0; i < a.length; i++) {
				int j = rand.nextInt(i + 1);
				long t = a[i];
				a[i] = a[j];
				a[j] = t;
			}
		}
	}

	static class BIT {
		long arr[];
		int n;

		public BIT(int a) {
			n = a;
			arr = new long[n];
		}

		long sum(int p) {
			long s = 0;
			while (p > 0) {
				s += arr[p];
				p -= p & (-p);
			}
			return s;
		}

		void add(int p, long v) {
			while (p < n) {
				arr[p] += v;
				p += p & (-p);
			}
		}
	}

	static class DSU {
		int[] arr;
		int[] sz;

		public DSU(int n) {
			arr = new int[n];
			sz = new int[n];
			for (int i = 0; i < n; i++)
				arr[i] = i;
			Arrays.fill(sz, 1);
		}

		public int find(int a) {
			if (arr[a] != a)
				arr[a] = find(arr[a]);
			return arr[a];
		}

		public void union(int a, int b) {
			int x = find(a);
			int y = find(b);
			if (x == y)
				return;
			arr[y] = x;
			sz[x] += sz[y];
		}

		public int size(int x) {
			return sz[find(x)];
		}
	}

	private static class InputReader {
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int zcurChar;
		private int znumChars;
		private SpaceCharFilter filter;

		public InputReader(InputStream stream) {
			this.stream = stream;
		}

		public int read() {
			if (znumChars == -1)
				throw new InputMismatchException();
			if (zcurChar >= znumChars) {
				zcurChar = 0;
				try {
					znumChars = stream.read(buf);
				} catch (IOException e) {
					throw new InputMismatchException();
				}
				if (znumChars <= 0)
					return -1;
			}
			return buf[zcurChar++];
		}

		public int nextInt() {
			int c = read();
			while (isSpaceChar(c))
				c = read();
			int sgn = 1;
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			int res = 0;
			do {
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				res *= 10;
				res += c - '0';
				c = read();
			} while (!isSpaceChar(c));
			return res * sgn;
		}

		public String nextString() {
			int c = read();
			while (isSpaceChar(c))
				c = read();
			StringBuilder res = new StringBuilder();
			do {
				res.appendCodePoint(c);
				c = read();
			} while (!isSpaceChar(c));
			return res.toString();
		}

		public double nextDouble() {
			int c = read();
			while (isSpaceChar(c))
				c = read();
			int sgn = 1;
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			double res = 0;
			while (!isSpaceChar(c) && c != '.') {
				if (c == 'e' || c == 'E')
					return res * Math.pow(10, nextInt());
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				res *= 10;
				res += c - '0';
				c = read();
			}
			if (c == '.') {
				c = read();
				double m = 1;
				while (!isSpaceChar(c)) {
					if (c == 'e' || c == 'E')
						return res * Math.pow(10, nextInt());
					if (c < '0' || c > '9')
						throw new InputMismatchException();
					m /= 10;
					res += (c - '0') * m;
					c = read();
				}
			}
			return res * sgn;
		}

		public long nextLong() {
			int c = read();
			while (isSpaceChar(c))
				c = read();
			int sgn = 1;
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			long res = 0;
			do {
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				res *= 10;
				res += c - '0';
				c = read();
			} while (!isSpaceChar(c));
			return res * sgn;
		}

		public boolean isSpaceChar(int c) {
			if (filter != null)
				return filter.isSpaceChar(c);
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}

		public String next() {
			return nextString();
		}

		public interface SpaceCharFilter {
			public boolean isSpaceChar(int ch);
		}

		public int[] readIntArray(int n) {
			int[] ret = new int[n];
			for (int i = 0; i < n; i++) {
				ret[i] = nextInt();
			}
			return ret;
		}
	}

	static class Dumper {
		static void print_int_arr(int[] arr) {
			for (int i = 0; i < arr.length; i++) {
				System.out.print(arr[i] + " ");
			}
			System.out.println();
			System.out.println("---------------------");
		}

		static void print_char_arr(char[] arr) {
			for (int i = 0; i < arr.length; i++) {
				System.out.print(arr[i] + " ");
			}
			System.out.println();
			System.out.println("---------------------");
		}

		static void print_double_arr(double[] arr) {
			for (int i = 0; i < arr.length; i++) {
				System.out.print(arr[i] + " ");
			}
			System.out.println();
			System.out.println("---------------------");
		}

		static void print_2d_arr(int[][] arr, int x, int y) {
			for (int i = 0; i < x; i++) {
				for (int j = 0; j < y; j++) {
					System.out.print(arr[i][j] + " ");
				}
				System.out.println();
			}
			System.out.println();
			System.out.println("---------------------");
		}

		static void print_2d_arr(boolean[][] arr, int x, int y) {
			for (int i = 0; i < x; i++) {
				for (int j = 0; j < y; j++) {
					System.out.print(arr[i][j] + " ");
				}
				System.out.println();
			}
			System.out.println();
			System.out.println("---------------------");
		}

		static void print(Object o) {
			System.out.println(o.toString());
		}

		static void getc() {
			System.out.println("here");
		}
	}
}