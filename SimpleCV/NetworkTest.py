import os
import glob
import copy
import time
import re
from socket import *

myPort = 111
myHost = '10.25.57.6'
s = socket(AF_INET, SOCK_STREAM)
s.bind((myHost, myPort))
s.listen(5)
i = 0

while True:
	if i < 10:
		i += 1
	else:
		connection, address = s.accept()
		connection.send((str("Very Wow")))
		i = 0
	