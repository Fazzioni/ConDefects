n = int(input())
r = list(map(int, input().split()))
c = list(map(int, input().split()))

q = int(input())
ans = ""
for i in range(q):

    a, b = map(int, input().split())
    a-=1
    b-=1

    if(r[a]+c[b] >= n+1):ans += "#"
    else:ans += "."

print(ans)