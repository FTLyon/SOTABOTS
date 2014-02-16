from socket import *

myPort = 111
myHost = '10.25.57.10'
s = socket(AF_INET, SOCK_STREAM)
s.bind((myHost, myPort))
s.listen(1)

def send(num):
	connection, address = s.accept()
	connection.send((str(num)))
	
	
