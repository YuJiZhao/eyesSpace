import Process from "@/modules/process";
import { CardDirection, CardType, Cards, CardList } from "@/constant";

interface sideCardsInterface {
    direction: CardDirection;
    cardType?: CardType;
    cards?: Array<Cards>;
    cardList?: CardList;
    follow?: boolean;
}

type useProcessControlType = (
    header?: boolean,
    sideCard?: false | sideCardsInterface,
    footer?: boolean
) => void;

const useProcessControl: useProcessControlType = (
    header = true,
    sideCard = false,
    footer = true
) => {
    Process.headerStatus.value = header;

    if(typeof sideCard === "boolean" && sideCard === false ) {
        Process.sideCardStatus.value = false;
    } else if(typeof sideCard === "object") {
        Process.sideCardStatus.value = true;
        Process.sideCardPosition.value = sideCard.direction;
        Process.sideCardType.value = sideCard.cardType == null ? CardType.Cards : sideCard.cardType;
        Process.sideCardChoice.value = sideCard.cards || [];
        if(sideCard.cardList != null) {
            Process.sideCardList.value = sideCard.cardList;
        }
        Process.sideCardFollow.value = sideCard.follow == null ? false : sideCard.follow;
    }
    
    Process.footerStatus.value = footer;
}

export default useProcessControl;