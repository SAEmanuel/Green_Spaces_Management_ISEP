# US027 - Show an organized list of all green spaces managed by manager


## 4. Tests
###
#### ------------------------------- Validations to Sort Green Spaces --------------------------------------

**Test 1:** Sort Green Spaces  Ascending By Name

    @Test
    void sortGreenSpacesAscendingByName() throws IOException {
        String responsible = "gsm@this.app";
        List<GreenSpace> sortedGreenSpaces = SortGreenSpaces.sortGreenSpaces("Ascending By Name", greenSpaceRepository.getGreenSpacesByResponsible(responsible));
        assertNotNull(sortedGreenSpaces);
        assertEquals(infanteDomHenrique, sortedGreenSpaces.get(0));
        assertEquals(palacioDeCristal, sortedGreenSpaces.get(1));
        assertEquals(parqueDaCidade, sortedGreenSpaces.get(2));
    }

**Test 2:** Sort Green Spaces Descending By Name

    void sortGreenSpacesDescendingByName() throws IOException {
        String responsible = "gsm@this.app";
        List<GreenSpace> sortedGreenSpaces = SortGreenSpaces.sortGreenSpaces("Descending By Name", greenSpaceRepository.getGreenSpacesByResponsible(responsible));
        assertNotNull(sortedGreenSpaces);
        assertEquals(parqueDaCidade, sortedGreenSpaces.get(0));
        assertEquals(palacioDeCristal, sortedGreenSpaces.get(1));
        assertEquals(infanteDomHenrique, sortedGreenSpaces.get(2));
    }

**Test 3:** Sort Green Spaces with BubbleSort method

    @Test
    void sortGreenSpacesBubbleSort() throws IOException {
        String responsible = "gsm@this.app";
        List<GreenSpace> sortedGreenSpaces = SortGreenSpaces.sortGreenSpaces("Bubble Sort", greenSpaceRepository.getGreenSpacesByResponsible(responsible));
        assertNotNull(sortedGreenSpaces);
        assertEquals(infanteDomHenrique, sortedGreenSpaces.get(0));
        assertEquals(parqueDaCidade, sortedGreenSpaces.get(1));
        assertEquals(palacioDeCristal, sortedGreenSpaces.get(2));
    }

**Test 4:** Sort Green Spaces with InsertionSort method

    @Test
    void sortGreenSpacesInsertionSort() throws IOException {
        String responsible = "gsm@this.app";
        List<GreenSpace> sortedGreenSpaces = SortGreenSpaces.sortGreenSpaces("Insertion Sort", greenSpaceRepository.getGreenSpacesByResponsible(responsible));
        assertNotNull(sortedGreenSpaces);
        assertEquals(infanteDomHenrique, sortedGreenSpaces.get(0));
        assertEquals(parqueDaCidade, sortedGreenSpaces.get(1));
        assertEquals(palacioDeCristal, sortedGreenSpaces.get(2));
    }

**Test 5:** try sort Green Spaces making an Invalid Method exception

    @Test
    void sortGreenSpacesInvalidMethod() throws IOException {
        String responsible = "gsm@this.app";
        List<GreenSpace> sortedGreenSpaces = SortGreenSpaces.sortGreenSpaces("Invalid Method", greenSpaceRepository.getGreenSpacesByResponsible(responsible));
        assertNull(sortedGreenSpaces);
    }

**Test 6:** Check if getting Sort Types works as well

    @Test
    void getSortTypes() throws IOException {
        List<String> sortTypes = SortGreenSpaces.getSortTypes();
        assertNotNull(sortTypes);
        assertTrue(sortTypes.contains("Ascending By Name"));
        assertTrue(sortTypes.contains("Descending By Name"));
        assertTrue(sortTypes.contains("Bubble Sort"));
        assertTrue(sortTypes.contains("Insertion Sort"));
    }



## 5. Construction (Implementation)

### Class ShowGreenSpacesUI

```java
    private void submitData() {
        List<GreenSpace> result = getController().showGreenSpaces(sortType);
    
        if (result != null) {
            for(GreenSpace gs : result)
                System.out.println(ANSI_BRIGHT_BLUE + gs.getName() + ANSI_RESET);
        } else {
            System.out.printf(ANSI_BRIGHT_RED + "\nGreen Spaces couldn't be shown" + ANSI_RESET);
        }
    }
```

### Class ShowGreenSpacesByManagerController
```java
    public List<GreenSpace> showGreenSpaces(String sortOrder) {
        String resposible = getResponsible();
    
        List<GreenSpace> greenSpaces = greenSpaceRepository.getGreenSpacesByResponsible(resposible);
        if(greenSpaces == null || greenSpaces.isEmpty()){
            System.out.printf(ANSI_BRIGHT_RED + "\nGreen Spaces list not found or empty" + ANSI_RESET);
            return null;
        }
    
        try {
            greenSpaces = sortGreenSpaces.sortGreenSpaces( sortOrder, greenSpaces);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return greenSpaces;
    }
```

### Class GreenSpaceRepository

```java
     public List<GreenSpace>  getGreenSpacesByResponsible (String responsible) {
        ArrayList<GreenSpace> greenSpaces = new ArrayList<>();
    
        for (GreenSpace greenSpace : greenSpacesList) {
            if (greenSpace.getResponsible().equals(responsible)) {
                greenSpaces.add(greenSpace);
            }
        }
        return greenSpaces;
    }
```

### Class SortGreenSpaces

```java
    private static void loadFileConfig() throws IOException {
        config = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/config.properties")) {
            config.load(fis);
        }
    }
    
    public static List<GreenSpace> sortGreenSpaces( String sortingMethod, List<GreenSpace> greenSpaces) throws IOException {
        loadFileConfig();
        boolean sorted = false;
    
        for (String key : config.stringPropertyNames()) {
            if (key.startsWith("sorting.")) {
                String value = config.getProperty(key);
    
                if(value.equalsIgnoreCase(sortingMethod)){
                    switch (sortingMethod){
                        case "Ascending By Name":
                            greenSpaces = ascendingName(greenSpaces);
                            sorted= true;
                            break;
                        case "Descending By Name":
                            greenSpaces = descendingName(greenSpaces);
                            sorted= true;
                            break;
                        case "Bubble Sort":
                            greenSpaces = bubbleSort(greenSpaces);
                            sorted= true;
                            break;
                        case "Insertion Sort":
                            greenSpaces = insertionSort(greenSpaces);
                            sorted= true;
                            break;
                    }
                }
            }
        }
    
        if(sorted)
            return greenSpaces;
        else{
            System.out.printf(ANSI_BRIGHT_RED + "\nNo sort method found" + ANSI_RESET);
            return null;
        }
    }
    
    private static List<GreenSpace> ascendingName(List<GreenSpace> greenSpaces) {
        Collections.sort(greenSpaces, Comparator.comparing(GreenSpace::getName));
        return greenSpaces;
    }
    
    private static List<GreenSpace> descendingName(List<GreenSpace> greenSpaces) {
        Collections.sort(greenSpaces, Comparator.comparing(GreenSpace::getName).reversed());
        return greenSpaces;
    }
    
    private static List<GreenSpace> bubbleSort(List<GreenSpace> greenSpaces) {
        List<GreenSpace> newGreenSpacesList = new ArrayList<>(greenSpaces);
        int n = newGreenSpacesList.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (newGreenSpacesList.get(j).getArea() < newGreenSpacesList.get(j + 1).getArea()) {
                    // Swap greenSpaces[j] and greenSpaces[j+1] for descending order
                    GreenSpace temp = newGreenSpacesList.get(j);
                    newGreenSpacesList.set(j, newGreenSpacesList.get(j + 1));
                    newGreenSpacesList.set(j + 1, temp);
                }
            }
        }
        return newGreenSpacesList;
    }
    
    private static List<GreenSpace> insertionSort(List<GreenSpace> greenSpaces) {
        List<GreenSpace> newGreenSpacesList = new ArrayList<>(greenSpaces);
        int n = newGreenSpacesList.size();
        for (int i = 1; i < n; ++i) {
            GreenSpace key = newGreenSpacesList.get(i);
            int j = i - 1;
    
            // Move elements of arr[0..i-1], that are greater than key, to one position ahead
            // of their current position
            while (j >= 0 && newGreenSpacesList.get(j).getArea() < key.getArea()) {
                newGreenSpacesList.set(j + 1, newGreenSpacesList.get(j));
                j = j - 1;
            }
            newGreenSpacesList.set(j + 1, key);
        }
    
        return newGreenSpacesList;
    }
    
    public static List<String> getSortTypes() throws IOException {
        loadFileConfig();
        List<String> sortTypes = new ArrayList<>();
    
        for (String key : config.stringPropertyNames()) {
            if (key.startsWith("sorting.")) {
                String value = config.getProperty(key);
                if(!value.equalsIgnoreCase("false"))
                    sortTypes.add(value);
            }
        }
    
        return sortTypes;
    }
```