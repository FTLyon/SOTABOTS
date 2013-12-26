import os
import glob
import copy
import time
import re
from SimpleCV import *

cam = Camera()
avgX = [0] * 10000
avgY = [0] * 10000
ind  = 0
while True:
	sumX = 0
	sumY = 0
	img = cam.getImage()
	#img = img2.colorDistance(Color.BLACK).invert()
	blobs = img.findBlobs()
	for b in range(len(blobs)-1):
		squares = img.findBlobs()
		if (blobs[b].isSquare(tolerance=0.05,ratiotolerance=0.5)):
			#blobs[b] = blobs[b] - blobs[b].getEdgeImage()
			blobs[b].draw(color=Color.YELLOW)
			blobs[b].drawOutline(color=Color.RED,width=3,alpha=128)
			if (blobs[b].minRectX() != 0 and blobs[b].minRectY() != 0):
				if (b+1 < len(blobs) - 1):
					if (squares[b].perimeter() > squares[b+1].perimeter()):
						squares.pop(b+1)
					else:
						pass
				else:
					pass
				avgX[ind] = (blobs[b].minRectX())
				avgY[ind] = (blobs[b].minRectY())
			else:
				pass
			for i in range(len(avgX)):
				sumX += avgX[ind]
				sumY += avgY[ind]
			
		else:
			pass
	if (ind == 9999):
		ind = 0
	else:
		ind += 1
	
	img.drawText(('X:   ' + str(round(sumX / 10000)) + '   Y: ' + str(round(sumY / 10000))),fontsize=40)
	img.show()



	
	