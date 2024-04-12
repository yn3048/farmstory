// fetch GET용
async function fetchGet(url){

    console.log("fetchData1...1");

    try{
        console.log("fetchData1...2");
        const response = await fetch(url);
        console.log("here1");

        if(!response.ok){
            console.log("here2");
            throw new Error('response not ok');
        }

        const data = await response.json();
        console.log("data1 : " + data);

        return data;

    }catch (err) {
        console.log(err)
    }
}

// fetch POST용
async function fetchPost(url, jsonData){

    console.log("fetchData2...1");

    try{
        console.log("fetchData2...2");
        const response = await fetch(url, {
            method: 'POST',
            headers: {"Content-type":"application/json"},
            body: JSON.stringify(jsonData)
        });
        console.log("fetchData2...3");

        if(!response.ok){
            console.log("fetchData2...4");
            throw new Error('response not ok');
        }

        const data = await response.json();
        console.log("fetchData2...5 : " + data);

        return data;

    }catch (err) {
        console.log(err)
        return  null;
    }
}

// fetch DELETE 용
async function fetchDelete(url){

    try {
        const response = await fetch(url, {
            method: 'DELETE'
        });

        if(!response.ok){
            throw new Error('response not ok');
        }

        const data = await response.json();
        console.log("data1 : " + data);

        return data;

    }catch(err){
        console.log(err)
    }
}

<!--🎈회원가입 모달창-->
function showModal(message){
    const modal = document.getElementById('resultModal');
    modal.getElementsByClassName('modal-body')[0].innerText = message;
    const resultModal = new bootstrap.Modal(modal);
    resultModal.show();
}

function alertModal(message){
    const modal = document.getElementById('alertModal');
    modal.getElementsByClassName('modal-body')[0].innerText = message;
    const resultModal = new bootstrap.Modal(modal);
    resultModal.show();
}

function confirmModal(message) {

    const modal = document.getElementById('confirmModal');
    modal.getElementsByClassName('modal-body')[0].innerText = message;
    const resultModal = new bootstrap.Modal(modal);
    resultModal.show(); // 모달 열기

    // 결과값 반환
    return new Promise(resolve => {
        // 확인 버튼 클릭 시
        document.getElementById('btnOk').onclick = function () {
            resultModal.hide(); // 모달 닫기
            resolve(true); // 확인 결과값 반환
        };

        // 취소 버튼 클릭 시
        document.getElementById('btnCancel').onclick = function () {
            resultModal.hide(); // 모달 닫기
            resolve(false); // 취소 결과값 반환
        };
    });
}

function showInputValid(inputs){
    for(const input of inputs){
        input.classList.remove('is-invalid');
        input.classList.add('is-valid');
    }
}

function showInputInvalid(inputs){
    for(const input of inputs) {
        input.classList.remove('is-valid');
        input.classList.add('is-invalid');
    }
}

function showResultValid(result, message){
    result.classList.remove('invalid-feedback');
    result.classList.add('valid-feedback');
    result.innerText = message;
}

function showResultInvalid(result, message){
    result.classList.remove('valid-feedback');
    result.classList.add('invalid-feedback');
    result.innerText = message;
}


<!--🎈다음 지도검색-->
function postcode() {
    new daum.Postcode({
        oncomplete: function (data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if (data.userSelectedType === 'R') {
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if (extraAddr !== '') {
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                //document.getElementById("sample6_extraAddress").value = extraAddr;

            } else {
                //document.getElementById("sample6_extraAddress").value = '';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('inputZip').value = data.zonecode;
            document.getElementById("inputAddr1").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("inputAddr2").focus();
        }
    }).open();

}

<!--🎈top버튼-->
window.onload = function () {
    let Top = document.getElementById('top');

    if (Top) {
        Top.style.display = 'none'
        window.addEventListener('scroll', function (){
            if(window.scrollY > 300) {
                Top.style.display = 'block';
                console.log(Top);
            } else {
                Top.style.display = 'none';
            }
        });

        Top.addEventListener('click', function (e) {
            e.preventDefault();
            window.scrollTo({top:0, behavior:'smooth'});
            console.log(Top);
        });
    } else {
        console.log("#top 요소를 찾을 수 없습니다.");
    }

    const mypage = document.getElementById('mypage');
    const cart = document.getElementById('cart');

    if(mypage){
        mypage.onclick = async function (e){
            e.preventDefault();
            const answer = await confirmModal("로그인 화면으로 이동하시겠습니까?");
            if(answer == true){
                location.href = '/farmstory/user/login';
            }
        }
    }
    if(cart){
        cart.onclick = async function (e){
            e.preventDefault();
            const answer = await confirmModal("로그인 화면으로 이동하시겠습니까?");
            if(answer == true){
                location.href = '/farmstory/user/login';
            }
        }
    }

}