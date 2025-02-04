# TODO

* movies, catalog
  - (DONE) movies, list all
  - (DONE) movies, list by genre
  - (DONE) movies, list by min (avg) rating
  - (DONE) movies, list by max (avg) rating

* users history
  - (DONE) user events, list all
  - (DONE) user events, list ratings only
  - (DONE) user events, list views only

* users interactions
  - (DONE) user events, on movie rated
  - (DONE) user events, on movie viewed
  - (DONE) user events, on any: user rating updated

* user recommendations
  - (DONE) recommend movies, similar to preferred genres
  - (TODO) recommend movies, preferred genres: highly rated
  - (DONE) recommend movies, not already rated
  - (TODO) user events, on any: recommendations adjusted
  - (TODO) [NTH] recommend movies, sorted (by events number)

* movies, search
  - (TODO) [NTH] movies, search by title, exact
  - (TODO) [NTH] movies, search by title, matching (at least one word)
  - (TODO) [NTH] movies, search by genres, matching (at least one)

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
- user preferred genres: genres of any rated movie
- user preferred highly rated: rating in (4, 5)