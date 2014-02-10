#!/usr/bin/python
import sys
import time
import signal
from SimpleCV import Camera, Color
cam = Camera(0, {"width":320, "height":240})
avgX = [0] * 100
avgY = [0] * 100
ind  = 0
StopGo = 1
count=0
#while True:
while ( count <= 5 ):
	count = count + 1
	sumX = 0
	sumY = 0
	# Attempt to capture a still frame from the camera for processing.
	try:
		img = cam.getImage()
	except:
		print "An error occured trying to create image"
		sys.exit(165)
	#img = img2.colorDistance(Color.BLACK).invert()
	# Let's look for blobs... Would squares be more efficient, considering we're getting railed for CPU on the pi?
	# TODO: I think there is a far more efficient way to determine squares and rectangles. Someone should look at optimizing this.
	try:
		img=img.edges()
		blobs = img.findBlobs()
		for b in range(len(blobs)-1):
			squares = img.findBlobs()
			if (blobs[b].isSquare(tolerance=0.09,ratiotolerance=0.9)):
				blobs[b].draw(color=Color.YELLOW)
				blobs[b].drawOutline(color=Color.RED,width=3,alpha=128)
#				print "Defining blobs."
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
##		img.drawText(('X:   ' + str(round(sumX / 100)) + '   Y: ' + str(round(sumY / 100))),fontsize=40)
		# Here we are going to display the results textually or graphically.
		try:
#			resX=str(round(sumX / 100)) 
#			resY=str(round(sumY / 100))
#			print "X=%s|Y=%s" % (resX, resY)
			img.show()
##			time.sleep(3)
#			# Let's try to save on memory here.
			del img
		except:
			print "Failed to properly render image."
			sys.exit(163)
#		curves=img.edges()
#		blobs=curves.findBlobs()
#		for blob in blobs:
#			blob.draw()
#		curves.show()
#		time.sleep(1)
#		del img
	except:
		print "something failed while finding geometry."
		sys.exit(164)
sys.exit()
