package CinemaAPP;

public class UpcomingMoviedata implements FilePersistable {
    private String name;
    private String genre;
    private String language;
    private String director;
    private String releaseDate;

    public UpcomingMoviedata(String name, String genre, String language, String director, String releaseDate) {
        this.name = name;
        this.genre = genre;
        this.language = language;
        this.director = director;
        this.releaseDate = releaseDate;
    }

    public String getName() { return name; }
    public String getGenre() { return genre; }
    public String getLanguage() { return language; }
    public String getDirector() { return director; }
    public String getReleaseDate() { return releaseDate; }

    @Override
    public String toFileString() {
        return name + "," + genre + "," + language + "," + director + "," + releaseDate;
    }
}
