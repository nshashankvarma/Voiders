import cv2
import pytesseract

pytesseract.pytesseract.tesseract_cmd = 'C:\\Program Files\\Tesseract-OCR\\tesseract.exe'
image_identifier = cv2.imread('test8.png')
image_identifier = cv2.cvtColor(image_identifier,cv2.COLOR_BGR2RGB)
#print(pytesseract.image_to_string(image_identifier))

###Detecting characters
#heiImg, WidImg,_ = image_identifier.shape
#boxes = pytesseract.image_to_boxes(image_identifier)
#for b in boxes.splitlines():
    ##print(b)
    #b = b.split(' ')
    #x,y,w,h = int(b[1]),int(b[2]),int(b[3]),int(b[4])
    #cv2.rectangle(image_identifier,(x,heiImg-y),(w,heiImg-h),(0,0,255),1)
    #cv2.putText(image_identifier,b[0],(x,heiImg-y+25),cv2.FONT_HERSHEY_COMPLEX,1,(50,50,255),1)
#cv2.imshow('Showcase ',image_identifier)
#cv2.waitKey(0)

###Detecting words
#heiImg, WidImg,_ = image_identifier.shape
#boxes = pytesseract.image_to_data(image_identifier)
#for x,b in enumerate(boxes.splitlines()):
    ##print(b)
    #if x!=0:
        #b = b.split()
        #print(b)
        #if len(b)==12:
            #x,y,w,h = int(b[6]),int(b[7]),int(b[8]),int(b[9])
            #cv2.rectangle(image_identifier,(x,y),(w+x,h+y),(0,0,255),1)
            #cv2.putText(image_identifier,b[11],(x,y),cv2.FONT_HERSHEY_COMPLEX,1,(50,50,255),1)
#cv2.imshow('Showcase ',image_identifier)
#cv2.waitKey(0)

###Detecting digits only
#heiImg, WidImg,_ = image_identifier.shape
#cong = r'--oem 3 --psm 6 outputbase digits'
#boxes = pytesseract.image_to_data(image_identifier,config = cong)
#for x,b in enumerate(boxes.splitlines()):
    ##print(b)
    #if x!=0:
       # b = b.split()
        #print(b)
        #if len(b)==12:
           # x,y,w,h = int(b[6]),int(b[7]),int(b[8]),int(b[9])
            #cv2.rectangle(image_identifier,(x,y),(w+x,h+y),(0,0,255),1)
            #cv2.putText(image_identifier,b[11],(x,y),cv2.FONT_HERSHEY_COMPLEX,1,(50,50,255),1)
#cv2.imshow('Showcase ',image_identifier)
#cv2.waitKey(0)

###Detecting individual digits as characters
#heiImg, WidImg,_ = image_identifier.shape
#cong = r'--oem 3 --psm 6 outputbase digits'
#boxes = pytesseract.image_to_boxes(image_identifier, config=cong)
#for b in boxes.splitlines():
    ##print(b)
    #b = b.split(' ')
    #x,y,w,h = int(b[1]),int(b[2]),int(b[3]),int(b[4])
    #cv2.rectangle(image_identifier,(x,heiImg-y),(w,heiImg-h),(0,0,255),1)
    #cv2.putText(image_identifier,b[0],(x,heiImg-y+25),cv2.FONT_HERSHEY_COMPLEX,1,(50,50,255),1)
#cv2.imshow('Showcase ',image_identifier)
#cv2.waitKey(0)


