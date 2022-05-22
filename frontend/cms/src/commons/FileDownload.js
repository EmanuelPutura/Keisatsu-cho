export default function downloadFile(paperObj){
    fetch("http://localhost:8080/papers/getFullPaper?paperId="+paperObj.id)
        .then(response => response !== null ? response.blob() : null)
        .then((response) => {
            if(response.size === 0){
                alert("File was not uploaded!");
            } else {
                const url = window.URL.createObjectURL(response);
                const link = document.createElement('a');
                link.href = url;
                link.setAttribute('download', `${paperObj.title}.pdf`)
                document.body.appendChild(link);
                link.click();
            }
        });
}