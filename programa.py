import sys
def main():
    a = False
    b = False
    c = ""
    x = 0
    y = 0
    z = 0
	a = True
	b = False
	x = 5
	y = 20
	z = y+x*2
	while (x<10 or y>3):
    	x = x+1
    	y = y-2
    	print(x)
    	print(y)

	if (x>=2 and z!=3):
		x = 10
	else:
		y = 5*z

	while True:
		x = x-1
		if not (y>3):
			break


if __name__ == "__main__":
    main()

