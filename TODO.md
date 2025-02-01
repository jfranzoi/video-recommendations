# TODO

* movies
  - (DONE) movies, list all
  - (DONE) movies, list by genre
  - (TODO) movies, list by min (avg) rating
  - (TODO) movies, list by max (avg) rating
  - (TODO) [NTH] movies, search by title, exact
  - (TODO) [NTH] movies, search by title, matching (at least one word)
  - (TODO) [NTH] movies, search by genres, matching (at least one)

* users interactions (history)
  - (TODO) user events, list all
  - (TODO) user events, list ratings only
  - (TODO) user events, list views only

  - (TODO) user events, on movie rated
  - (TODO) user events, on movie viewed
  - (TODO) user events, on any: recommendations adjusted

* user recommendations
  - (TODO) recommend movies, not rated
  - (TODO) recommend movies, by user preferred genres
  - (TODO) [NTH] recommend movies, sorted (by events number)

* deliverables
  - (DONE) git repo
  - (DONE) data files
  - (DONE) docker compose
  - (DONE) README.md
  - (TODO) [NTH] API spec
  - (TODO) [NTH] DB schema: DB design choices
  - (TODO) [NTH] service metrics exposed + logging

## NOTES
- views, percentage: between 0 and 100
- ratings, explicit: between 1 (disliked) and 5 (liked)
- ratings, implicit: 4 for view percentage in (60..80), 5 for view percentage in (80..100)
- ratings, priority: explicit if available, implicit otherwise
- user preferred genres: genres of any rated movie
- user preferred highly rated: rate in (4, 5)