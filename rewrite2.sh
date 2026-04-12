git filter-branch -f --env-filter '
if [ "$GIT_AUTHOR_NAME" = "Surya Gupta" ] || [ "$GIT_AUTHOR_NAME" = "Surya Dev Gupta" ] || [ "$GIT_AUTHOR_NAME" = "Suryaguptaa" ]; then
    export GIT_AUTHOR_NAME="Suryaguptaa"
    export GIT_AUTHOR_EMAIL="suryagupta.ws@gmail.com"
    export GIT_COMMITTER_NAME="Suryaguptaa"
    export GIT_COMMITTER_EMAIL="suryagupta.ws@gmail.com"
fi
' -- --all
