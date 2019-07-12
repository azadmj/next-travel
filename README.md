# next-travel
Rest Client App 

Run the all the tests using Maven -- 
    mvn test -Duser.timezone=US/Central
    
 Run NextBus app  - 
    MainClass com.azad.trips.NextBus is mapped in pom.xml to exe.MainClass <br>
    mvn clean exec:java -Dexec.args="'ROUTE NAME' 'BUS STOP' 'DIRECTION'" -Duser.timezone=US/Central
    
    mvn clean compile exec:java -Dexec.args="'METRO Blue Line' 'Target Field Station Platform 1' 'south'" -Duser.timezone=US/Central
    
    Below are the main Classes involded for this module - sequence of Classes and methods.
    -NextBus
       -NextBusFinderService.timeToNextBus()
          -validateRoute()
            -ResponseMapperService.findRoutes()
              --NextTripApiConsumer.executeHttpGet()
          -validateStopName()
            -ResponseMapperService.populateResponse()
              -NextTripApiConsumer.executeHttpGet()
          -validateDirection()
            -ResponseMapperService.populateResponse()
              -NextTripApiConsumer.executeHttpGet()
          -nextDepartureTime()
              
 
            
