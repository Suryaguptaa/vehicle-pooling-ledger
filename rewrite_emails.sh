git filter-branch -f --env-filter '
if [ "$GIT_AUTHOR_EMAIL" = "181776742+Suryaguptaa@users.noreply.github.com" ]; then
    export GIT_AUTHOR_EMAIL="suryagupta.ws@gmail.com"
    export GIT_AUTHOR_NAME="Surya Gupta"
fi
if [ "$GIT_COMMITTER_EMAIL" = "181776742+Suryaguptaa@users.noreply.github.com" ]; then
    export GIT_COMMITTER_EMAIL="suryagupta.ws@gmail.com"
    export GIT_COMMITTER_NAME="Surya Gupta"
fi
' -- HEAD
